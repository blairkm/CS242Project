package edu.ucr.cs242.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rehmke
 */
public class Config {    
    
    public static final boolean DEBUG = true;
    public static final boolean CONTINUOUS = true;

    public static final String UNSPLASH_API_PHOTOS_ENDPOINT = "https://api.unsplash.com" + "/photos";
    public static final String UNSPLASH_API_LATEST_ENDPOINT = UNSPLASH_API_PHOTOS_ENDPOINT;
    public static final String UNSPLASH_API_RANDOM_ENDPOINT = "https://api.unsplash.com" + "/photos/random";
    public static final String UNSPLASH_API_USER_ENDPOINT = "https://api.unsplash.com" + "/users";
    public static final String UNSPLASH_API_ENDPOINT = UNSPLASH_API_RANDOM_ENDPOINT;  
    
    private static String UNSPLASH_ACCESS_KEY = "";
    public static final String UNSPLASH_SECRET_KEY = null;
    
    public static final String ARCHIVE_FILEPATH_ROOT = FileUtil.getWorkingDirectory() + System.getProperty("file.separator") + "DATA";
    public static final String METADATA_ARCHIVE_FILEPATH = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "METADATA";
    public static final String IMAGE_ARCHIVE_FILEPATH = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "IMAGE";
    public static final String INDEXABLE_ARCHIVE_FILEPATH = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "INDEXABLE";
    public static final String INDEXED_ARCHIVE_FILEPATH = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "INDEXED";
    public static final String METADATA_ARCHIVE = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "METADATA.TXT";
    public static final String INDEXING_PERFORMANCE_ARCHIVE = ARCHIVE_FILEPATH_ROOT + System.getProperty("file.separator") + "INDEX_PERFORMANCE.TXT";
    
    public static final int MAX_PHOTO_RESPONSE_COUNT = 30; // default enumeration max
    public static final int PERIODIC_REQUEST_LIMIT = 50; // mode: trial = 50; production = 5000
    public static final int RANDOM_NUMBER_FLOOR = 1; // for page request
    public static final int RANDOM_NUMBER_CEILING = 100; // for page request
    
    public static final int REQUEST_DELAY_POLITE = 3000;
    public static final int REQUEST_DELAY_LIMIT = 60*60*1000;
    public static final List<Integer> DELAY_CODES = new ArrayList <>(Arrays.asList(403, 202, 429));      
    
    public static void setUnsplashAccessKey(String _val) {
        Config.UNSPLASH_ACCESS_KEY = _val;
    }
    
    public static String getUnsplashAccessKey() {
        return Config.UNSPLASH_ACCESS_KEY;
    }
    
}
