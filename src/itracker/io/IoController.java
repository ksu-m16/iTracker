/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.io;

import itracker.io.common.IRequest;
import itracker.io.common.IResultListener;
import itracker.io.file.FileManager;
import itracker.io.manager.AbstractIoManager;
import itracker.io.manager.IoManager;
import itracker.io.mem.MemManager;
import itracker.io.net.NetManager;
import itracker.util.Log;
import itracker.util.Text;
import itracker.util.Time;
import java.io.File;

/**
 *
 * @author KerneL
 */
public class IoController {    
    
    public static class Options {

        public static class Network {
            public String host = "";
            public int port = 0;
            public long netRetryTimeout = 60;
        }

        public static class Cache {
            public String dataFolder = "";
            public long fileSizeLimit = 64 * 1024;
            public long memoryLimit = 1024 * 1024;
        }

        public Network network = new Network();
        public Cache cache = new Cache();
        public long ioUpdateInterval = 300;    
    }    
    
    public static class Status {
        public boolean networkReady;
        public int networkInQueue;        
        public boolean fileCacheReady;
        public int fileCacheInQueue;
        public boolean fileCacheEmpty;
        public boolean memCacheReady;
        public int memCacheInQueue;
        public boolean memCacheEmpty;
        public long memCacheUsed;
    }
    
    Options options = new Options();
    Status status = new Status();
    
    private NetManager nm = new NetManager();
    private FileManager fm = new FileManager();
    private MemManager mm = new MemManager();    
    
    IoManager[] iStack = {nm, fm, mm};
    IoManager[] mStack = {nm, fm};
    IoManager[] fStack = {nm};
    
    class IoUpdater extends Thread {
        private boolean running = true;
        
        public boolean checkIoManager(AbstractIoManager iom) {
            if (iom.hasErrors()) {
                Log.d(this.getClass(), "io[" + iom.getName() + "] reported errors: " + iom.getErrorString());
            }
            Log.d(this.getClass(), "restarting io[" + iom.getName() + "]");
            iom.restart();
            return true;
        }
        
        public boolean flushMemCache() {
            if (!fm.isReady()) {
                return false;
            }
            if (mm.isCacheQueueEmpty()) {
                return false;
            }
            IRequest rq = mm.getMoveRequest();
            if (rq == null) {
                return false;
            }
            new IORequest(rq, mStack).schedule();
            return true;
        }
        
        public boolean flushFileCache() {
            if (!nm.isReady()) {
                return false;
            }
            if (fm.isCacheQueueEmpty()) {
                return false;
            }
            IRequest rq = fm.getMoveRequest();
            if (rq == null) {
                return false;
            }
            new IORequest(rq, fStack).schedule();
            return true;
        }        
        
        @Override                                
        public void run() {            
            while(running) {                
                boolean hasTasks = true;
                while(running && hasTasks) {                    
                    hasTasks = false;
                    hasTasks |= flushMemCache();
                    hasTasks |= flushFileCache();                    
                    hasTasks |= checkIoManager(nm);
                    hasTasks |= checkIoManager(fm);
                    hasTasks |= checkIoManager(mm);                    
                }
                try {
                    synchronized(this) {
                        this.wait(options.ioUpdateInterval);
                    }
                } catch (InterruptedException ex) {                    
                }
            }
        }        
        
        public void terminate() {
            running = false;
            synchronized(this) {
                this.notify();
            }
        }
    }
    
    IoUpdater ioUpdater;
    private void startIoUpdater() {
        stopIoUpdater();
        ioUpdater = new IoUpdater();
        ioUpdater.start();
    }
    
    private void stopIoUpdater() {
        if (ioUpdater != null) {
            ioUpdater.terminate();                        
        }       
        ioUpdater = null;
    }
           
    private static class IORequest implements IRequest {        
        private static final IResultListener listener = new IResultListener() {
            @Override
            public void onSuccess(IoManager m, IRequest rq) {            
                Log.d(this.getClass(), "io[" + m.getName() + "] reported success for '" + rq.getName() + "'");
            }

            @Override
            public void onError(IoManager m, IRequest rq, String errorMessage) {
                if (!(rq instanceof IORequest)) {
                    return;
                }          
                IORequest iorq = (IORequest)rq;
                Log.d(this.getClass(), "io[" + m.getName() + "] reported error for '" 
                    + rq.getName() + "': " + errorMessage);
                
                if (iorq.schedule()) {
                    return;
                }
                synchronized(System.out) {
                    Log.d(this.getClass(), "io stack for '" + rq.getName() + "' is empty, data discarded");                    
                }
            }
        };
        
        private long timestamp = Time.current();
        private int stackIndex = 0;
        private IoManager[] stack;
        private IRequest request;
        private String data = null;
        private String name = "main";
        
        private void setName(String name) {
            this.name = name + "#" + Text.randomHash() + "@" + Text.formatTimestamp(timestamp);
        }
        
        public IORequest(IRequest rq, IoManager[] stack) {            
            this.stack = stack;
            this.request = rq;
            this.setName("retry");
        }
        
        public IORequest(String data, IoManager[] stack) {
            this.stack = stack;
            this.data = data;
            this.setName("input");
        }
        
        public boolean schedule() {
            if (stackIndex >= stack.length) {
                return false;
            }
            stack[stackIndex].store(this);
            stackIndex++;
            return true;
        }

        @Override
        public IResultListener getListener() {
            return listener;
        }

        @Override
        public String getData() {
            if (data != null) {
                return data;
            }
            return request.getData();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }                
    }
    
    public void startup() {
        cleanup();

        nm.setConnectionParams(options.network.host, options.network.port);
        nm.setRetryTimeout(options.network.netRetryTimeout);        
        fm.setDataFolder(new File(options.cache.dataFolder));        
        fm.setFileSizeLimit(options.cache.fileSizeLimit);           
        mm.setMemoryLimit(options.cache.memoryLimit);                
        
        nm.start();
        fm.start();
        mm.start();
        
        startIoUpdater();
    }
    
    public void cleanup() {
        nm.stop();
        fm.stop();
        mm.stop();        
        stopIoUpdater();
    }
    
    
    private void updateStatus() {
        status.networkReady = nm.isReady();
        status.networkInQueue = nm.getInputQueueSize();
        status.fileCacheReady = fm.isReady();
        status.fileCacheInQueue = fm.getInputQueueSize();
        status.fileCacheEmpty = fm.isCacheQueueEmpty();
        status.memCacheReady = mm.isReady();
        status.memCacheInQueue = mm.getInputQueueSize();
        status.memCacheUsed = mm.getMemoryUsed();
        status.memCacheEmpty = mm.isCacheQueueEmpty();
    }
    
    public Status getStatus() {
        updateStatus();
        return this.status;
    }
       
    public void update(String s) {
        new IORequest(s, iStack).schedule();
    }
    
    public void setOptions(Options options) {
        cleanup();        
        this.options = options;
        startup();
    }
}
