/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.util;

/**
 *
 * @author Igor Kernitskiy
 */
public class Location implements ILocation {
    private long time;
    private double lat;
    private double lon;
    private double alt;
    private double speed;
    private double course;
    private double accuracy;

    static public Location fromParams(long time, double lat, double lon, double alt,
        double speed, double course, double accuracy) {
        Location loc = new Location();
        loc.time = time;
        loc.lat = lat;
        loc.lon = lon;
        loc.alt = alt;
        loc.speed = speed;
        loc.course = course;
        loc.accuracy = accuracy;
        return loc;
    }
    
    static public Location fromLocation(ILocation l) {
        Location loc = new Location();
        loc.time = l.getTime();
        loc.lat = l.getLat();
        loc.lon = l.getLon();
        loc.alt = l.getAlt();
        loc.speed = l.getSpeed();
        loc.course = l.getCourse();
        loc.accuracy = l.getAccuracy();
        return loc;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public double getLat() {
        return lat;
    }

    @Override
    public double getLon() {
        return lon;
    }

    @Override
    public double getAlt() {
        return alt;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getCourse() {
        return course;
    }

    @Override
    public double getAccuracy() {
        return accuracy;
    }    
}
