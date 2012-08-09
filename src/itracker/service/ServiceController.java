/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.service;

import itracker.collect.CollectController;
import itracker.collect.common.IStoreListener;
import itracker.io.IoController;
import itracker.util.ILocation;
import itracker.util.Text;

/**
 *
 * @author KerneL
 */
public class ServiceController implements IServiceController {

    public static class Credentials {        
        String username = "";
        String password = "";
        String imei = "";
    }
    
    public static class Options {
        public Credentials credentials = new Credentials();
        public IoController.Options io = new IoController.Options();
        public CollectController.Options collect = new CollectController.Options();
    }
    
    public static class Status {
        public IoController.Status io = new IoController.Status();
        public CollectController.Status collect = new CollectController.Status();
    }
    
    Options options = new Options();    
    Status status = new Status();
    IoController io = new IoController();
    CollectController collect = new CollectController();            
    ILocationController locationController = null;
        
    @Override
    public void setLocationController(ILocationController lc) {
        locationController = lc;
    }
    
    IDataObserver dataObserver = new IDataObserver() {
        @Override
        public void onLocation(ILocation loc) {
            collect.update(loc);
        }

        @Override
        public void onAction(boolean state) {
            collect.onAction(state);
        }

        @Override
        public void onPower(boolean state) {
            collect.onPower(state);
        }

        @Override
        public void onKit(boolean state) {
            collect.onKit(state);
        }

        @Override
        public void onBattery(double state) {
            collect.onBattery(state);
        }
    };
    
    @Override
    public IDataObserver getDataObserver() {
        return dataObserver;
    }        
    
    
    public void startup() {
        collect.setStoreListener(new IStoreListener() {
            @Override
            public void store(ILocation l) {                
                String sample = String.format(
                    "<gps i=%s t=%l x=%s y=%s z=%s s=%s c=%s a=%s>", 
                    options.credentials.imei,
                    l.getTime(),
                    Text.formatDouble8(l.getLat()),
                    Text.formatDouble8(l.getLon()),
                    Text.formatDouble1(l.getAlt()),
                    Text.formatDouble1(l.getSpeed()),
                    Text.formatDouble1(l.getCourse()),
                    Text.formatDouble1(l.getAccuracy())
                );
                io.update(sample);
            }
        });
        collect.startup();
        io.startup();        
    }
    
    public Status getStatus() {
        status.io = io.getStatus();
        status.collect = collect.getStatus();
        return status;
    }
}

