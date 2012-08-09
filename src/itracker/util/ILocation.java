/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.util;

/**
 *
 * @author KerneL
 */
public interface ILocation {
    long getTime();
    double getLat();
    double getLon();
    double getAlt();
    double getSpeed();
    double getCourse();
    double getAccuracy();
}
