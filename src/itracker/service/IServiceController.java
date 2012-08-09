/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.service;

/**
 *
 * @author Igor Kernitskiy
 */
public interface IServiceController {    
    IDataObserver getDataObserver();
    void setLocationController(ILocationController lc);
}
