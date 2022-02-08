package edu.ucr.cs242.project.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author rehmke
 */
public class DateUtil {
    
    public static final String DATE_FORMAT = "";
    
    public static String getTimestamp() {        
        return ""+Instant.now();        
    }    
 
    public static String getFormattedDate(Date _ts, String _format) {
        String retVal = "";
        DateFormat dateFormat = new SimpleDateFormat(_format);
        retVal = dateFormat.format(_ts);        
        return retVal;
    }    
    
}