/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.service;

import itracker.util.ILocation;

/**
 *
 * @author Igor Kernitskiy
 */
public interface IDataObserver {
    public void onLocation(ILocation loc);
    public void onAction(boolean state);
    public void onPower(boolean state);
    public void onKit(boolean state);
    public void onBattery(double state);    
}
