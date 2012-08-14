/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.strategy;

import itracker.util.ILocation;
import itracker.collect.common.IStoreListener;
import itracker.collect.strategy.checked.MotionDetector;
import itracker.util.Log;
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
    private long testInterval = 300;
    private long testDuration = 30;
    private int retryAccuracyCount = 4;
    
    private long lastUpdateTime = 0;
    private long lastTestTime = 0;
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

    public void setTestInterval(long testInterval) {
        this.testInterval = testInterval;
    }

    public void setTestDuration(long testDuration) {
        this.testDuration = testDuration;
        configureMotionDetector();
    }
    
    private void configureMotionDetector() {        
        motionDetector.setSampleSize((int)testDuration);
        motionDetector.setMotionContinuedTestSamples(
            (int)(Math.ceil((double) stopTimeout / (double) testDuration)));        
        motionDetector.setMotionDetectedAccuracySamples(retryAccuracyCount);
    }

    
    public CheckedStrategy(IStoreListener l) {
        super(l);
    }
    
    private void stopCollection() {
        stopped = true;
        retry = false;
        lastTestTime = Time.current();
    }
    
    private void startCollection() {
        for(ILocation l : cachedLocations) {
            super.update(l);            
        }
        lastUpdateTime = Time.current();
        stopped = false;        
    }    
    
    private boolean isReadyStarted() {
        if (Time.current() - lastUpdateTime > noLocationTimeout * 1000) {
            stopCollection();
            return false;
        }

        if (Time.current() - lastTestTime < testDuration * 1000) {
            return true;
        }       
        
        lastTestTime = Time.current();        
        motionDetector.update(testLocations);
        testLocations.clear();        
        
        if (motionDetector.isStopDetected()) {
            stopCollection();
            return false;
        }
        
        return true;
    }
    
    boolean retry = false;
    List<ILocation> testLocations = new LinkedList<ILocation>();
    List<ILocation> cachedLocations = new LinkedList<ILocation>();
    private boolean isReadyStopped() {
        //check if we are not in retry attempt
        if (!retry) {
            //if duration for next retry is elapsed then start retry attempt
            if (Time.current() - lastTestTime > testInterval * 1000) {
                lastTestTime = Time.current();
                retryCount = 0;
                testLocations.clear();
                cachedLocations.clear();
                retry = true;
                return true;
            }

            return false;
        }        
        
        //check if retry duration is not elapsed
        if (Time.current() - lastTestTime < testDuration * 1000) {
            return true;
        }
        lastTestTime = Time.current();
        
        motionDetector.update(testLocations);
        cachedLocations.addAll(testLocations);
        testLocations.clear();
        
        if (motionDetector.isMotionDetected()) {            
            startCollection();
            return true;
        }
        
        //check if accuracy is good, then wait for next retry
        if (motionDetector.isAccuracyGood()) {
            //check if we can still collect samples for good accuracy test
            retryCount++;
            Log.d(this, "accuracy is good, retry #" + retryCount);
            if (retryCount < retryAccuracyCount) {
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
        testLocations.add(loc);
        
        if (stopped) {            
            return;
        } 

        super.update(loc);        
    }

    @Override
    public void reset() {
        testLocations.clear();        
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
