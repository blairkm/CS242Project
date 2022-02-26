package edu.ucr.cs242.project;

import java.time.Instant;

/**
 *
 * @author rehmke
 */
public class DateUtil {
 
    public static String getTimestamp() {        
        return ""+Instant.now();        
    }
    
}