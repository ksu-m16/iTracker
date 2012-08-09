/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.strategy;

/**
 *
 * @author KerneL
 */
public class IdleStrategy extends AbstractStrategy {

    public IdleStrategy() {
        super(null);
    }        
    
    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public String getName() {
        return "idle";
    }        
}
