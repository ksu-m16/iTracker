/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.manager;

import itracker.io.common.IRequest;
import itracker.io.common.IRequest;

/**
 *
 * @author KerneL
 */
public interface IoManager {
    boolean store(IRequest request);
    int getInputQueueSize();
    String getName();
}
