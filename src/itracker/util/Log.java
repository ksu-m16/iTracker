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
}
