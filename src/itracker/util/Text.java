/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 *
 * @author KerneL
 */
public class Text {
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    
    public static String formatCurrentTimestamp() {
        return formatTimestamp(System.currentTimeMillis());
    }
    
    public static String formatTimestamp(long millis) {
        Date date = new Date(millis);
        return dateFormat.format(date);
    }
        
    static final Random rng = new Random(System.currentTimeMillis());
    public static String randomHash() {
        int val = rng.nextInt();
        return String.format("%08x", val);
    }
    
    static final NumberFormat double8 = NumberFormat.getInstance(Locale.ENGLISH);
    static {
        double8.setMinimumFractionDigits(8);
        double8.setMaximumFractionDigits(8);        
    }
    public static String formatDouble8(double val) {
        return double8.format(val);
    }
       
    static final NumberFormat double1 = NumberFormat.getInstance(Locale.ENGLISH);
    static {
        double1.setMinimumFractionDigits(1);
        double1.setMaximumFractionDigits(1);
    }
    public static String formatDouble1(double val) {
        return double1.format(val);
    }
            
}
