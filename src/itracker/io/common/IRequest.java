/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.common;

/**
 *
 * @author KerneL
 */
public interface IRequest {
    IResultListener getListener();
    String getData();
    String getName();
    long getTimestamp();
}
