/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.net;

import itracker.io.manager.AbstractIoManager;
import itracker.io.common.IRequest;
import itracker.util.Time;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author KerneL
 */
public class NetManager extends AbstractIoManager {
    
    private long retryTimeout = 300;
    private String host = "";
    private int port = 0;    
    
    private Socket socket;        
    private long lastConnectAttemptTime = 0;

    public void setRetryTimeout(long retryTimeout) {
        this.retryTimeout = retryTimeout;
    }

    public long getRetryTimeout() {
        return retryTimeout;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }    
    
    public void setConnectionParams(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public long timeTillReconnect() {
        if (socket != null) {
            return 0;
        }
        return Time.current() - lastConnectAttemptTime;
    }

    private synchronized void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
        socket = null;
        lastConnectAttemptTime = 0;
    }
    
    private synchronized boolean checkConnection() {
        if (socket != null) {
            if (!socket.isClosed() && !socket.isOutputShutdown()) {
                return true;
            }
            closeConnection();
            socket = null;
        }
        
        if (Time.current() - lastConnectAttemptTime < retryTimeout) {
            return false;
        }
        lastConnectAttemptTime = Time.current();
        
        try {
            Socket s = new Socket(host, port);
        } catch (UnknownHostException ex) {
            return false;
        } catch (IOException ex) {            
            return false;
        }        
        
        return true;
    }
    
    @Override
    protected void startup() {
        checkConnection();
    }

    @Override
    protected void cleanup() {
        closeConnection();
    }

    @Override
    protected String process(IRequest request) {
        if (!checkConnection()) {        
            return "network not available";
        }
        String data = request.getData();
        if (data == null) {
            return "empty data";
        }
        
        try {
            OutputStream os = socket.getOutputStream();             
            os.write(data.getBytes());
            lastConnectAttemptTime = Time.current();
        } catch (IOException ex) {
            closeConnection();            
        }
        
        return null;
    }
    
    public boolean isReady() {
        checkConnection();
        return (socket != null);
    }

    @Override
    public String getName() {
        return "net";
    }    
    
}