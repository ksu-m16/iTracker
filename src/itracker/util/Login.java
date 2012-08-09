/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KerneL
 */
public class Login {
    private String host;
    private String imei;
    private String device;
    private String os;    
    private String username;
    private String password;        
    
    public void setHost(String host) {
        this.host = host;        
    }
    public void setCredentials(String username, String password, String imei) {
        this.username = username;
        this.password = password;
        this.imei = imei;
    }
    public void setDevice(String device, String os) {
        this.device = device;
        this.os = os;
    }        
    
    public static class LoginResponse {
        public static class Error {
            String id = "";
            String message = "no login attempt performed";
            String description = "";
        }        
        boolean success;
        Error error = new Error();
    }
    
    LoginResponse response = new LoginResponse();
    
    public boolean run(int timeout) {
        URL url;
        try {            
            url = new URL("http://" + host + "/tracker_activation.php"
                + "?imei=" + imei
                + "&login=" + username
                + "&password=" + password
                + "&device=" + device
                + "&os=" + os);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(timeout);
            conn.connect();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));            
            
            String result = "";
            for (String s = br.readLine(); s != null; s = br.readLine()) {
                result += s;
            }
                        
            Gson gson = new Gson();
            response = gson.fromJson(result, LoginResponse.class);                                            
        } catch (Exception ex) {                        
            response = new LoginResponse();
            response.error.message = "Connection problem";
            response.error.description = ex.toString();            
        }                         
        return response.success;
    }
    
    public LoginResponse getResponse() {
        return response;
    }    
}