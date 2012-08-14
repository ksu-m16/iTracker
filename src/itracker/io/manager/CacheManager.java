/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.manager;

import itracker.io.common.Request;
import itracker.io.common.IRequest;
import itracker.io.common.IResultListener;
import itracker.util.Log;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author KerneL
 */

public abstract class CacheManager extends AbstractIoManager {
    
    protected interface Entry {
        public abstract String getData();
        public abstract String getName();   
        public abstract void remove();
    }
    
    Queue<Entry> cacheQueue = new ConcurrentLinkedQueue<Entry>();    
        
    protected void purgeCacheQueue() {
        cacheQueue.clear();        
    }
    
    public boolean isCacheQueueEmpty() {
        return cacheQueue.isEmpty();
    }

    protected void push(Entry e) {            
        cacheQueue.add(e);
    }
    
    private void popEntry(Entry e) {        
        if (cacheQueue.remove(e)) {            
            e.remove();
        }
    }    

    static class MoveRequest extends Request {
        final Entry entry;
        final CacheManager manager;
        
        static final IResultListener listener = new IResultListener() {
            @Override
            public void onSuccess(IoManager m, IRequest rq) {                
                if (!(rq instanceof MoveRequest)) {
                    return;
                }
                MoveRequest mrq = (MoveRequest)rq;
                mrq.remove();                                
            }

            @Override
            public void onError(IoManager m, IRequest rq, String errorMessage) {            
            }
        };
                
        public MoveRequest(CacheManager m, Entry e) {
            super("move(" + e.getName() + ")", e.getData(), listener);            
            entry = e;
            manager = m;
        }                
    
        void remove() {
            manager.popEntry(entry);
        }
    }
    
    public synchronized IRequest getMoveRequest() {
        Entry e = cacheQueue.peek();
        return (e != null)?new MoveRequest(this, e):null;
    }
}
