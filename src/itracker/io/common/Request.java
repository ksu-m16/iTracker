/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.common;

/**
 *
 * @author KerneL
 */
public class Request implements IRequest {
    private final String name;
    private final String data;
    private IResultListener listener;
    private final long timestamp = System.currentTimeMillis();

    public Request(String name, String data, IResultListener l) {
        this.name = name;
        this.data = data;
        this.listener = l;
    }        
    
    @Override
    public IResultListener getListener() {
        return listener;
    }

    @Override
    public String getData() {
        return data;
    }       

    @Override
    public String getName() {
        return name;
    }    
    
    public long getTimestamp() {
        return timestamp;
    }
}
