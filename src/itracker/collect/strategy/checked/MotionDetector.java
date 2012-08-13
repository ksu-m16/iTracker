/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.strategy.checked;

import itracker.util.ILocation;
import itracker.util.Log;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Igor Kernitskiy
 */

class MotionRecord {
    long time;
    int badSpeedCount;
    int badAccuracyCount;
    int totalCount;
    int originalCount;
    boolean isBadSpeed;
    boolean isBadAccuracy;

    @Override
    public String toString() {
        return "time: " + time        
            + "; points: " + originalCount
            + "; total: " + totalCount            
            + "; bsc: " + badSpeedCount 
            + "; bac: " + badAccuracyCount
            + "; bs: " + isBadSpeed
            + "; ba: " + isBadAccuracy;
    }        
}

public class MotionDetector {   
    
    static final String TAG = "MotionDetector";
    
    /**
     * Expected number of location points in single sample
     * List passed to updated method. Sample list is supposed
     * to be contiguous
     */
    private int sampleSize = 30;
    /**
     * Slow speed threshold in m/s
     * Location point with speed below this value is decided to be "bad"
     */
    private double badSpeedThreshold = 1.;
    /**
     * Low accuracy threshold in m
     * Location point with accuracy above this value is decided to be "bad"
     */
    private double badAccuracyThreshold = 20.;    
    /**
     * Ratio of bad speed points in sample
     * Sample with number of bad speed points above 
     * this ratio is decided to have bad speed
     */
    private double badSpeedCountThreshold = 0.5;
    /**
     * Ratio of bad speed points in sample
     * Sample with number of bad accuracy points above 
     * this ratio is decided to have bad accuracy
     */    
    private double badAccuracyCountThreshold = 0.5;
    
    /**
     * Length of sample info (MotionRecord) history list
     */
    private int maxHistoryLength = 25;
    /**
     * Maximal time gap in ms, allowed between start points 
     * of two consequent samples in history list
     * If next sample has time distance from previous greater  
     * than this value history list if flushed.
     */
    private long maxHistoryTimeGap = 250000;
    
    /* 
     * Motion continued test parameters
     * Test fails in following cases:
     *  - both numbers of bad speed samples and bad accuracy samples
     *    are above allowed value;
     *  - speed is exactly zero in all points in all tested samples;
     */
    /**
     * Number of samples to be tested
     */
    private int motionContinuedTestSamples = 20;
    /**
     * Maximal number of bad speed samples 
     * to consider speed as still good
     */
    private double motionContinuedBadSpeedAllowed = 0.5;
    /**
     * Maximal number of bad accuracy samples 
     * to consider accuracy is still good
     */
    private double motionContinuedBadAccuracyAllowed = 0.5;
    
    /* 
     * Motion detected test parameters
     * Test passed in following cases:
     *  - last sample speed is registered as good;
     *  - last 'motionDetectedAccuracySamples' accuracy 
     *    are good and at least single sample of them 
     *    has good speed
     */
    
    /**
     * Minimal number of samples in history required to run test
     */
    private int motionDetectedTestSamples = 1;
    /**
     * Minimal number of samples required to run test for good
     * accuracy and non-bad speed
     */
    private int motionDetectedAccuracySamples = 4;
    
    /**
     * Status of last motion continued test
     */
    private boolean motionContinued = true;
    /**
     * Status of last motion detected test
     */    
    private boolean motionDetected = false;
    
    /**
     * History list of samples
     */
    private LinkedList<MotionRecord> history = new LinkedList<MotionRecord>();

    /**
     * If this status is false, then data collection should be stopped
     * @return Negated status of last motion continued test
     */
    public boolean isStopDetected() {
        return !motionContinued;
    }
    
    /**
     * If this status is true, then data collection should be started
     * @return Status of last motion detection test
     */
    public boolean isMotionDetected() {
        return motionDetected;
    }    
    
    /**
     * @return true if accuracy of last sample is considered to be good
     */
    public boolean isAccuracyGood() {
        if (history.size() == 0) {
            return false;
        }
        return !history.getFirst().isBadAccuracy;
    }
    
    /**
     * @return true if speed of last sample is considered to be good
     */
    public boolean isSpeedGood() {
        if (history.size() == 0) {            
            return false;
        }        
        return !history.getFirst().isBadSpeed;
    }   
    
    /**
     * Updates MotionDetector with next sample of Location points
     * @param loc List of Location points collected for at least 
     * sampleSize seconds. In case of good GPS signal should be close
     * to sampleSize value.
     */
    public void update(List<ILocation> loc) {
        int len = loc.size();
        if (len == 0) {
            Log.d(this.getClass(), "update: empty location sample");
            return;
        }
        
        MotionRecord mr = new MotionRecord();
        mr.time = loc.get(0).getTime();        
        mr.badAccuracyCount = Math.max(0, sampleSize - len);
        mr.badSpeedCount = Math.max(0, sampleSize - len);
        
        for (ILocation l : loc) {            
            if (l.getSpeed() < badSpeedThreshold) {
                mr.badSpeedCount++;
            }
            if (l.getAccuracy() > badAccuracyThreshold) {
                mr.badAccuracyCount++;
            }
        }        
        mr.originalCount = len;
        mr.totalCount = Math.max(len, sampleSize);        
        mr.isBadSpeed = mr.badSpeedCount > mr.totalCount * badSpeedCountThreshold;        
        mr.isBadAccuracy = mr.badAccuracyCount > mr.totalCount * badAccuracyCountThreshold;        
        
        Log.d(this.getClass(), "update: " + mr);
        
        updateHistory(mr);
    }
    
    /**
     * Evaluates motion continues state
     */
    void updateMotionContinued() {
        motionContinued = true;
        
        if (history.size() < motionContinuedTestSamples) {
            Log.d(this.getClass(), "updateMotionContinued: not enough samples (mc: true)");
            return;
        }
        
        int total = 0;
        int badAccuracy = 0;
        int badSpeed = 0;
        int badSpeedCount = 0;
        int totalCount = 0;
        for (MotionRecord mr : history) {
            badAccuracy += mr.isBadAccuracy?1:0;
            badSpeed += mr.isBadSpeed?1:0;            
            badSpeedCount += mr.badSpeedCount;
            totalCount += mr.totalCount;            
            if (total == motionContinuedTestSamples) {
                break;
            }            
            total++;
        }
        
        if ((badSpeed > total * motionContinuedBadSpeedAllowed)
         && (badAccuracy > total * motionContinuedBadAccuracyAllowed)) {
            Log.d(this.getClass(), "updateMotionContinued: bad speed + bad accuracy (mc: false)");
            motionContinued = false;
            return;
        }
        
        if (badSpeedCount >= totalCount) {
            Log.d(this.getClass(), "updateMotionContinued: completely bad speed (mc: false)");
            motionContinued = false;
            return;
        }
        
        Log.d(this.getClass(), "updateMotionDetected: all tested passed (mc: true)");
    }
    
    /**
     * Evaluates motion detected state
     */
    void updateMotionDetected() {
        motionDetected = false;
        if (history.size() < motionDetectedTestSamples) {
            Log.d(this.getClass(), "updateMotionDetected: not enough samples (md: false)");
            return;
        }
        
        if (isSpeedGood()) {            
            motionDetected = true;
            Log.d(this.getClass(), "updateMotionDetected: speed is good (md: true)");
            return;
        }
        
        if (history.size() < motionDetectedAccuracySamples) {
            Log.d(this.getClass(), "updateMotionDetected: not enough samples for accuracy test (md: false)");
            return;
        }

        int total = 0;
        int badAccuracy = 0;        
        int badSpeed = 0;
        for (MotionRecord mr : history) {
            badAccuracy += mr.isBadAccuracy?1:0;
            badSpeed += mr.isBadSpeed?1:0;
            total++;           
            if (total == motionDetectedAccuracySamples) {
                break;
            }            
        }
                        
        if ((badAccuracy == 0) 
         && (badSpeed != motionDetectedAccuracySamples)) {
            motionDetected = true;
            Log.d(this.getClass(), "updateMotionDetected: good accuracy and speed present (md: true)");
            return;
        }
        
        Log.d(this.getClass(), "updateMotionDetected: all motion tests failed (md: false)");
    }
    
    /**
     * Runs motion tests to update Detector status
     */
    void updateStatus() {
        updateMotionContinued();
        updateMotionDetected();
        Log.d(this.getClass(), "updateStatus: mc: " + motionContinued + "; md: " + motionDetected);
    }
    
    /**
     * Updates history list, checking for time gaps 
     * and flushing it if required
     * @param mr Last motion record to be inserted into history list
     */
    void updateHistory(MotionRecord mr) {
        if (history.size() > 0) {
            long oldTime = history.getFirst().time;
            if (mr.time - oldTime >= maxHistoryTimeGap) {
                Log.d(this.getClass(), "updateHistory: history flushed, gap: " + (mr.time - oldTime));
                history.clear();
            }
        }
        Log.d(this.getClass(), "updateHistory: history size is " + history.size());
        while (history.size() >= maxHistoryLength) {                
            history.removeLast();
        }
        history.addFirst(mr);
        updateStatus();
    }
    
    
    public int getSampleSize() {
        return sampleSize;
    }
    
    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public double getBadAccuracyThreshold() {
        return badAccuracyThreshold;
    }       
    
    public void setBadAccuracyThreshold(double badAccuracyThreshold) {
        this.badAccuracyThreshold = badAccuracyThreshold;
    }

    public double getBadSpeedThreshold() {
        return badSpeedThreshold;
    }

    public void setBadSpeedThreshold(double badSpeedThreshold) {
        this.badSpeedThreshold = badSpeedThreshold;
    }

    public double getBadSpeedCountThreshold() {
        return badSpeedCountThreshold;
    }

    public void setBadSpeedCountThreshold(double badSpeedCountThreshold) {
        this.badSpeedCountThreshold = badSpeedCountThreshold;
    }

    public double getBadAccuracyCountThreshold() {
        return badAccuracyCountThreshold;
    }

    public void setBadAccuracyCountThreshold(double badAccuracyCountThreshold) {
        this.badAccuracyCountThreshold = badAccuracyCountThreshold;
    }

    public int getMaxHistoryLength() {
        return maxHistoryLength;
    }

    public void setMaxHistoryLength(int maxHistoryLength) {
        this.maxHistoryLength = maxHistoryLength;
    }

    public long getMaxHistoryTimeGap() {
        return maxHistoryTimeGap;
    }

    public void setMaxHistoryTimeGap(long maxHistoryTimeGap) {
        this.maxHistoryTimeGap = maxHistoryTimeGap;
    }

    public double getMotionContinuedBadAccuracyAllowed() {
        return motionContinuedBadAccuracyAllowed;
    }

    public void setMotionContinuedBadAccuracyAllowed(double motionContinuedBadAccuracyAllowed) {
        this.motionContinuedBadAccuracyAllowed = motionContinuedBadAccuracyAllowed;
    }

    public double getMotionContinuedBadSpeedAllowed() {
        return motionContinuedBadSpeedAllowed;
    }

    public void setMotionContinuedBadSpeedAllowed(double motionContinuedBadSpeedAllowed) {
        this.motionContinuedBadSpeedAllowed = motionContinuedBadSpeedAllowed;
    }

    public int getMotionContinuedTestSamples() {
        return motionContinuedTestSamples;
    }

    public void setMotionContinuedTestSamples(int motionContinuedTestSamples) {
        this.motionContinuedTestSamples = motionContinuedTestSamples;
    }

    public int getMotionDetectedTestSamples() {
        return motionDetectedTestSamples;
    }

    public void setMotionDetectedTestSamples(int motionDetectedTestSamples) {
        this.motionDetectedTestSamples = motionDetectedTestSamples;
    }

    public int getMotionDetectedAccuracySamples() {
        return motionDetectedAccuracySamples;        
    }

    public void setMotionDetectedAccuracySamples(int motionDetectedAccuracySamples) {
        this.motionDetectedAccuracySamples = motionDetectedAccuracySamples;
    }

    public void reset() {
        motionContinued = true;
        motionDetected = false;
        history.clear();
    }
    
}
