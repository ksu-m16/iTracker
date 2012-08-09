/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io.mem;

import itracker.io.common.IRequest;
import itracker.io.manager.CacheManager;

/**
 *
 * @author KerneL
 */
public class MemManager extends CacheManager {

    class MemEntry implements Entry {
        private String data;
        private int length;
        private long timestamp = System.currentTimeMillis();

        public MemEntry(String data) {
            this.data = data;            
            this.length = data.length();
        }                

        @Override
        public String getData() {
            return data;
        }

        @Override
        public String getName() {
            return "mem[" + length + "]@" + timestamp;
        }

        @Override
        public synchronized void remove() {           
            MemManager.this.totalSize -= data.length();
        }
    }
    
    long memoryLimit = 1024*1024;
    long totalSize = 0;
    
    @Override
    protected synchronized String process(IRequest request) {
        if (totalSize > memoryLimit) {
            return "memory limit of " + memoryLimit + " bytes exceeded";
        }
        
        String s = request.getData();
        if (s == null) {
            return "empty data";
        }
        
        totalSize += s.length();
        push(new MemEntry(s));        
        return null;
    }
    
    
    public boolean isReady() {
        return (totalSize < memoryLimit);
    }

    @Override
    public String getName() {
        return "mem";
    }

    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public long getMemoryLimit() {
        return memoryLimit;
    }        
    
    public long getMemoryUsed() {
        return totalSize;
    }

}
