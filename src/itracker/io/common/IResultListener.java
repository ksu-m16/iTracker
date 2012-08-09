/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.common;

import itracker.io.manager.IoManager;

/**
 *
 * @author KerneL
 */
public interface IResultListener {
    void onSuccess(IoManager m, IRequest rq);
    void onError(IoManager m, IRequest rq, String errorMessage);
}
