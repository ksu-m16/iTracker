/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.strategy;

import itracker.collect.common.IStoreListener;

/**
 *
 * @author KerneL
 */
public class CollectStrategy extends AbstractStrategy {    
    public CollectStrategy(IStoreListener l) {
        super(l);
    }    
    
    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public String getName() {
        return "collect";
    }        
}
