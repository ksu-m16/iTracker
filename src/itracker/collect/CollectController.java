/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect;

import itracker.collect.common.IStoreListener;
import itracker.util.ILocation;
import itracker.collect.strategy.CheckedStrategy;
import itracker.collect.strategy.CollectStrategy;
import itracker.collect.strategy.IStrategy;
import itracker.collect.strategy.IdleStrategy;
import itracker.collect.fsm.FsmController;
import itracker.util.Location;

/**
 *
 * @author KerneL
 */
public class CollectController {
    
    public static class Options {
        public static class AutoCollect {

            public double minBattery = 30;
            public long stopTimeout = 600;
            public long noDataTimeout = 600;
            public long testInterval = 300;
            public long testDuration = 30;

            @Override
            public Object clone() {
                AutoCollect ac = new AutoCollect();
                ac.minBattery = minBattery;
                ac.stopTimeout = stopTimeout;
                ac.testInterval = testInterval;
                ac.testDuration = testDuration;
                return ac;
            }                        
        }
        public AutoCollect auto = new AutoCollect();
        public boolean autoCollect = true;
        public double minBattery = 20;

        @Override
        public Object clone() {
            Options o = new Options();
            o.auto = (AutoCollect)auto.clone();
            o.autoCollect = autoCollect;
            o.minBattery = minBattery;
            return o;
        }                
    }
    
    public static class Status {
        public boolean action = false;
        public boolean power = false;
        public boolean kit = false;
        public double battery = 100;        
        public String state = "UNKNOWN";
        public String strategy = "UNKNOWN";
        public boolean ready = true;
        public Location location = new Location();
    }
     
    private Options options = new Options();
    private Status status = new Status();    

    private IStoreListener storeListener;
    private IStoreListener listener = new IStoreListener() {
        @Override
        public void store(ILocation l) {
            if (storeListener == null) {
                return;                    
            }
            storeListener.store(l);                
        }
    };

    public void setStoreListener(IStoreListener storeListener) {
        this.storeListener = storeListener;
    }

    public IStoreListener getStoreListener() {
        return storeListener;
    }                

    private IdleStrategy idleStrategy = new IdleStrategy();
    private CheckedStrategy checkedStrategy = new CheckedStrategy(listener);
    private CollectStrategy collectStrategy = new CollectStrategy(listener);
    private IStrategy currentStrategy = idleStrategy;    

    private boolean updateStrategy(FsmController.State state) {
        status.state = state.name();
        IStrategy strategy = idleStrategy;
        switch(state) {
            case RUN:
            case RUN_P:
            case RUN_K:   
                strategy = checkedStrategy;                    
                break;
            case RUN_KP:  
                strategy = collectStrategy;
                break;
        }            
        if (strategy == currentStrategy) {
            return false;
        }        
        currentStrategy = strategy;        
        currentStrategy.reset();        
        status.strategy = currentStrategy.getName();
        return true;
    }
                            
    FsmController fsm = new FsmController();    
    
    public void startup() {
    }
    
    public void cleanup() {
    }

    public void setOptions(Options options) {
        this.options = options;
        checkedStrategy.setNoLocationTimeout(options.auto.noDataTimeout);
        checkedStrategy.setTestDuration(options.auto.testDuration);
        checkedStrategy.setTestInterval(options.auto.testInterval);
        checkedStrategy.setStopTimeout(options.auto.stopTimeout);
        checkedStrategy.reset();
    }            
    
    public void onAction(boolean state) {        
        status.action = state;
        fsm.update(state?FsmController.Input.START:FsmController.Input.STOP);
        updateStrategy(fsm.getState());
    }
    
    public void onPower(boolean state) {
        status.power = state;
        fsm.update(state?FsmController.Input.POWER_ON:FsmController.Input.POWER_OFF);    
        updateStrategy(fsm.getState());
    }
    
    public void onKit(boolean state) {    
        status.kit = state;
        fsm.update(state?FsmController.Input.KIT_ON:FsmController.Input.KIT_OFF);
        updateStrategy(fsm.getState());    
    }
    
    public void onBattery(double state) {
        status.battery = state;
    }
    
    public boolean isReady() {
        boolean ready = currentStrategy.isReady();
        ready &= (status.battery > options.minBattery);
        return ready;
    }
    
    public void update(ILocation l) {
        status.location = Location.fromLocation(l);        
        currentStrategy.update(l);        
    }
    
    public Status getStatus() {        
        status.ready = isReady();
        return status;
    }
}
