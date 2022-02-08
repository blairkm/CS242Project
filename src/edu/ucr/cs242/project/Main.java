package edu.ucr.cs242.project;

import java.io.File;

/**
 *
 * @author rehmke
 */
public class Main {
    
    /*    
        @setup

        1) Update value assigned to Config.UNSPLASH_ACCESS_KEY with a plain-text Unsplash Access Key or an encrypted Unsplash Access Key (see EncDec.java)
        2) Update value assigned to Config.ARCHIVE_FILEPATH_ROOT with a valid download directory target
    
    */    
 
    public static void main(String[] args) {
        init();

        // source: random feed
        // UnsplashApiRandom.perform();
        
        // source: editorial feed
        UnsplashApiCrawler.perform();
                
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