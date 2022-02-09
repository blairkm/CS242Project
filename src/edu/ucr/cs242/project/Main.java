package edu.ucr.cs242.project;

import java.io.File;
import java.util.Date;
import edu.ucr.cs242.project.util.DateUtil;
import edu.ucr.cs242.project.util.StringUtil;

/**
 *
 * @author rehmke
 */
public class Main {
 
    public static void main(String[] args) {
        
        StringUtil.announce("* Starting at " + DateUtil.getFormattedDate(new Date(), "h:mm:ss a") + "...");
        
        init(args);

        // source: random feed
        // UnsplashApiRandom.perform();
        
        // source: editorial feed
        UnsplashApiCrawler.perform();
        
        StringUtil.announce("* Finishing at " + DateUtil.getFormattedDate(new Date(), "h:mm:ss a") + "...");
                
    }
    
    private static void init(String[] _args) {
        
        if (_args.length == 1) {
            Config.setUnsplashAccessKey(_args[0]);
            try {
                new File(Config.METADATA_ARCHIVE_FILEPATH).mkdirs();
                new File(Config.IMAGE_ARCHIVE_FILEPATH).mkdirs();
                new File(Config.INDEXABLE_ARCHIVE_FILEPATH).mkdirs();
                new File(Config.INDEXED_ARCHIVE_FILEPATH).mkdirs();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }            
        } else {
            System.out.println("Error: An Unsplash Access Key is required to continue.");
        }
        
    }
    
}