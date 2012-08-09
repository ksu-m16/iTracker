/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.manager;

import itracker.io.common.IRequest;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author KerneL
 */
public abstract class AbstractIoManager implements IoManager {    
    private final Queue<IRequest> inQueue = new ConcurrentLinkedQueue<IRequest>();            

    private IOThread thread = null;
    Exception startupException = null;
    Exception processException = null;
    Exception cleanupException = null;        
    Exception runException = null;    
    
    private class IOThread extends Thread {
        boolean running = false;
        boolean ready = false;        
        
        private boolean startup() {
            try {
                running = true;
                AbstractIoManager.this.startup();
            } catch(Exception ex) {
                running = false;
                startupException = ex;                                
            }   
            return running;
        }
        
        private void cleanup() {
            try {             
                AbstractIoManager.this.cleanup();
            } catch(Exception ex) {
                cleanupException = ex;
            }            
        }
        
        private void process() {  
            if (!running) {
                return;
            }
            
            try {
                IRequest rq = inQueue.peek();
                while (running && (rq != null)) {            
                    String error = AbstractIoManager.this.process(rq);
                    if (error == null) {
                        rq.getListener().onSuccess(AbstractIoManager.this, rq);
                    } else {
                        rq.getListener().onError(AbstractIoManager.this, rq, error);
                    }
                    inQueue.poll();
                    rq = inQueue.peek();
                }
            } catch(Exception ex) {
                processException = ex;
            }            
        }
        
        @Override        
        public void run() {            
            try {
                if (!startup()) {
                    return;
                }            

                ready = true;
                while (running) {
                    waitForRequest();
                    process();
                }
                ready = false;    
                
            } catch(Exception ex) {
                runException = ex;
            } finally {       
                running = false;
                ready = false;                              
                cleanup();
            }
        }        
    }
    
    
    protected void waitForRequest() {
        synchronized(inQueue) {
            try {
                inQueue.wait();
            } catch (InterruptedException ex) {                
            }
        }       
    }
    
    protected void notifyOfRequest() {
        synchronized(inQueue) {
            inQueue.notify();
        }       
    }
   
    public synchronized void start() {
        stop();
        cleanup();
        startup();

        startupException = null;        
        runException = null;
        processException = null;        
        cleanupException = null;       
        
        thread = new IOThread();
        thread.start();
    }
      
    public synchronized void stop() {
        if (thread == null) {
            return;
        }
        thread.running = false;
        notifyOfRequest();
    }
    
    public synchronized void restart() {        
        start();
    }
    
    public synchronized boolean isRunning() {
        if (thread == null) {
            return false;
        }
        return thread.isAlive();        
    }
    
    @Override
    public boolean store(IRequest request) {        
        inQueue.add(request);        
        notifyOfRequest();
        return true;
    }   

    @Override
    public int getInputQueueSize() {
        return inQueue.size();
    }    
        
    protected void startup() {}    
    protected void cleanup() {}
    abstract protected String process(IRequest request);
    
    public boolean hasErrors() {
        return (startupException != null)
            || (runException != null)
            || (processException != null)
            || (cleanupException != null);       

    }
    
    private String getErrorString(String name, Exception ex) {
        if (ex == null) {
            return "";
        }
        return name + "#" + ex.toString() + " '" + ex.getMessage() + "' ";
    }
    
    public String getErrorString() {
        return getErrorString("startup", startupException)
            + getErrorString("run", runException)
            + getErrorString("process", processException)
            + getErrorString("cleanup", cleanupException);
    }
}
