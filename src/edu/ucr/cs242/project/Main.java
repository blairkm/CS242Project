package edu.ucr.cs242.project;

import edu.ucr.cs242.project.util.DateUtil;
import java.io.File;
import edu.ucr.cs242.project.util.StringUtil;
import java.util.Date;

/**
 *
 * @author rehmke
 */
public class Main {
 
    public static void main(String[] args) {
        
        StringUtil.announce("* Starting at " + DateUtil.getFormattedDate(new Date(), "h:mm:ss a") + "...");
        
        init();

        // source: random feed
        // UnsplashApiRandom.perform();
        
        // source: editorial feed
        UnsplashApiCrawler.perform();
        
        StringUtil.announce("* Finishing at " + DateUtil.getFormattedDate(new Date(), "h:mm:ss a") + "...");
                
    }
    
    private static void init() {
        try {
            new File(Config.METADATA_ARCHIVE_FILEPATH).mkdirs();
            new File(Config.IMAGE_ARCHIVE_FILEPATH).mkdirs();
            new File(Config.INDEXABLE_ARCHIVE_FILEPATH).mkdirs();
            new File(Config.INDEXED_ARCHIVE_FILEPATH).mkdirs();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    
}