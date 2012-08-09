/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.strategy;

import itracker.util.ILocation;

/**
 *
 * @author KerneL
 */
public interface IStrategy {
    void reset();
    boolean isReady();
    void update(ILocation loc);
    String getName();
}
