/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.strategy;

import itracker.util.ILocation;
import itracker.collect.common.IStoreListener;
import itracker.collect.strategy.checked.MotionDetector;
import itracker.util.Time;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author KerneL
 */
public class CheckedStrategy extends AbstractStrategy {
    private long noLocationTimeout = 300;
    private long stopTimeout = 600;
    private long retryInterval = 300;
    private long retryDuration = 30;
    private int retryAccuracyCount = 4;
    
    private long lastUpdateTime = 0;
    private long lastRetryTime = 0;
    private int retryCount = 0;
    private boolean stopped = false;
    
    MotionDetector motionDetector = new MotionDetector();

    public void setNoLocationTimeout(long noLocationTimeout) {
        this.noLocationTimeout = noLocationTimeout;
    }

    public void setStopTimeout(long stopTimeout) {
        this.stopTimeout = stopTimeout;
        configureMotionDetector();
    }

    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public void setRetryDuration(long retryDuration) {
        this.retryDuration = retryDuration;
        configureMotionDetector();
    }
    
    private void configureMotionDetector() {        
        motionDetector.setSampleSize((int)retryDuration);
        motionDetector.setMotionContinuedTestSamples(
            (int)(Math.ceil((double) stopTimeout / (double) retryDuration)));        
        motionDetector.setMotionDetectedAccuracySamples(retryAccuracyCount);
    }

    
    public CheckedStrategy(IStoreListener l) {
        super(l);
    }
    
    private void stopCollection() {
        stopped = true;
        retry = false;
        lastRetryTime = Time.current();
    }
    
    private void startCollection() {
        for(ILocation l : cachedLocations) {
            super.update(l);            
        }
        stopped = false;        
    }    
    
    private boolean isReadyStarted() {
        if (Time.current() - lastUpdateTime > noLocationTimeout * 1000) {
            stopCollection();
            return false;
        }
        if (motionDetector.isStopDetected()) {
            stopCollection();
            return false;
        }
        return true;
    }
    
    boolean retry = false;
    List<ILocation> retryLocations = new LinkedList<ILocation>();
    List<ILocation> cachedLocations = new LinkedList<ILocation>();
    private boolean isReadyStopped() {
        //check if we are not in retry attempt
        if (!retry) {
            //if duration for next retry is elapsed then start retry attempt
            if (Time.current() - lastRetryTime > retryInterval * 1000) {
                lastRetryTime = Time.current();
                retryCount = 0;
                retryLocations.clear();
                cachedLocations.clear();
                retry = true;
                return true;
            }
        }        
        
        //check if retry duration is not elapsed
        if (Time.current() - lastRetryTime < retryDuration * 1000) {
            return true;
        }
        
        motionDetector.update(retryLocations);
        cachedLocations.addAll(retryLocations);
        retryLocations.clear();
        
        if (motionDetector.isMotionDetected()) {            
            startCollection();
            return true;
        }
        
        //check if accuracy is bad, then wait for next retry
        if (motionDetector.isAccuracyGood()) {
            //check if we can still collect samples for good accuracy test
            if (retryCount < retryAccuracyCount) {
                lastRetryTime = Time.current();
                return true;
            }            
        }

        retry = false;
        retryCount = 0;
        cachedLocations.clear();
        return false;
    }

    @Override
    public boolean isReady() {
        return (stopped)?isReadyStopped():isReadyStarted();
    }

    @Override
    public void update(ILocation loc) {                        
        lastUpdateTime = Time.current();

        if (stopped) {
            retryLocations.add(loc);
            return;
        } 

        super.update(loc);        
    }

    @Override
    public void reset() {
        retryLocations.clear();        
        cachedLocations.clear();        
        motionDetector.reset();
        startCollection();        
        super.reset();        
    }

    @Override
    public String getName() {
        return "checked";
    }        
}
