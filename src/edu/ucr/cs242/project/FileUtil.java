package edu.ucr.cs242.project;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author rehmke
 */
public class FileUtil {

    protected static void persistResponse(String _id, String _val) {
        
        try {
            
            // write metadata to file
            File file = new File(Config.METADATA_ARCHIVE_FILEPATH + System.getProperty("file.separator") + _id);
            FileUtils.writeStringToFile(file, _val, StandardCharsets.UTF_8, true);            
            
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }
        
    }
    
    protected static void persistMetadata(String _vals) {
        
        try {
            
            // write metadata to file
            File file = new File(Config.METADATA_ARCHIVE);
            FileUtils.writeStringToFile(file, _vals, StandardCharsets.UTF_8, true);            
            
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }
        
    }    
    
    protected static void persistIndexable(String _id, String _val) {
        
        try {
            
            // write indexable to file
            File file = new File(Config.INDEXABLE_ARCHIVE_FILEPATH + System.getProperty("file.separator") + _id);
            FileUtils.writeStringToFile(file, _val, StandardCharsets.UTF_8, true);            
            
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }
        
    }     
    
    protected static void persistImage(String _id, String _url) {
                
        try {
            
            // download image to memory
            URL url = new URL(_url);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            
            // write image to disk
            FileOutputStream fos = new FileOutputStream(Config.IMAGE_ARCHIVE_FILEPATH + System.getProperty("file.separator") + _id);
            fos.write(response);
            fos.close();            
            
        } catch (MalformedURLException mfex) {
            mfex.printStackTrace(System.err);
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }
        
    }
    
    public static boolean isDirectoryEmpty(String _filepath) {
        boolean isEmpty = false;        
        File f = new File(_filepath);
        if (f.listFiles().length == 0) {
            isEmpty = true;
        }        
        return isEmpty;      
    }
    
}