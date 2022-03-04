package edu.ucr.cs242.project.util;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rehmke
 */
public class StringUtil {
 
    public static void announce(String _val) {
        System.out.println(_val);
    }    
    
    public static String scrub(String _val) {
        String retVal = "";
        retVal = _val;
        retVal = StringUtils.replace(retVal, "_", "");
        retVal = StringUtils.replace(retVal, "\\r\\n", "");
        retVal = StringUtils.replace(retVal, "\r\n", "");
        retVal = StringUtils.replace(retVal, "\n", "");
        retVal = StringUtils.replace(retVal, "\\n", "");
        retVal = StringUtils.replace(retVal, "\\u0027", "");
        retVal = StringUtils.replace(retVal, "\u0027", "");
        return retVal;
    }    
    
}