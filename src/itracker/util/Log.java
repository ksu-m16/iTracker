/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.util;

/**
 *
 * @author Igor Kernitskiy
 */
public class Log {
    public static void d(Class source, String msg) {
        System.out.println(Text.formatCurrentTimestamp() + " [" + source.getSimpleName() + "]: " + msg);
    }
    
    public static void d(Object source, String msg) {        
        d(source.getClass(), msg);
    }

    public static void e(Object source, Exception ex) {        
        d(source.getClass(), ex.getClass().getSimpleName() + " : " + ex.getMessage());
    }    
}
