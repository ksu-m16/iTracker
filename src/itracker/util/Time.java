/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.util;

/**
 *
 * @author Igor Kernitskiy
 */
public class Time {            
    
    public interface ITimeProvider {
        long current();
    }
    
    public static class SystemTime implements ITimeProvider {        
        @Override
        public long current() {
            return System.currentTimeMillis();
        }       
        
        private static final SystemTime inst = new SystemTime();
        public static SystemTime getInstance() {
            return inst;
        }
    }
    
    public static class DebugTime implements ITimeProvider {
        public long time;
        @Override
        public long current() {
            return time;
        }
        
        private static final DebugTime inst = new DebugTime();
        public static DebugTime getInstance() {
            return inst;
        }       
    }
    
    private static ITimeProvider provider = SystemTime.getInstance();
    
    public static void setTimeProvider(ITimeProvider p) {
        provider = p;
    }
    
    public static ITimeProvider getTimeProvider() {
        return provider;
    }
    
    public static long current() {
        return provider.current();
    }    
}
