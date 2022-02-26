package edu.ucr.cs242.project.web;

/**
 *
 * @author rehmke
 */
public class QueryUtil {
 
    public static String process(String _val) {
        
        String retVal = "";
        
        // @todo - process query value        
        if (_val.equals("")) {
            retVal = "Error";
        } else {
            retVal = _val;    
        }        
        
        return retVal;
    }
    
}