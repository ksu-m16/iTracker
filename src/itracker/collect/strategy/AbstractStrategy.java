/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.strategy;

import itracker.util.ILocation;
import itracker.collect.common.IStoreListener;

/**
 *
 * @author KerneL
 */
public abstract class AbstractStrategy implements IStrategy {

    private IStoreListener listener;

    public AbstractStrategy(IStoreListener l) {
        listener = l;
    }            
    
    @Override
    public void reset() {        
    }

    @Override
    public void update(ILocation loc) {
        if (listener == null) {
            return;            
        }
        listener.store(loc);
    }
    
}
