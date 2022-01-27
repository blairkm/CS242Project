package edu.ucr.cs242.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rehmke
 */
public class Config {    
    
    protected static final boolean DEBUG = true;
    protected static final boolean CONTINUOUS = true;

    protected static final String UNSPLASH_API_ENDPOINT = "https://api.unsplash.com" + "/photos/random"; // another starting point besides random...?
    protected static final String UNSPLASH_ACCESS_KEY = EncDec.dec("XXXXX");
    protected static final String UNSPLASH_SECRET_KEY = null;
    
    protected static final String ARCHIVE_FILEPATH_ROOT = "/Users/rehmke/development/school/Cs242/DATA";
    protected static final String METADATA_ARCHIVE_FILEPATH = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "metadata";
    protected static final String IMAGE_ARCHIVE_FILEPATH = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "image";        
    
    protected static final int REQUEST_DELAY = 2000;    
    protected static final List<Integer> DELAY_CODES = new ArrayList <>(Arrays.asList(202,429));      
    
}
