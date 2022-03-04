package edu.ucr.cs242.project;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

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
    
    protected static void persistIndexableProcessed(String _id, String _val) {

        try {

            // write indexable to file
            File file = new File(Config.INDEXABLE_PROCESSED + System.getProperty("file.separator") + _id);
            FileUtils.writeStringToFile(file, _val, StandardCharsets.UTF_8, true);

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

    // @note: _duration = ms
    public static void persistIndexPerformance(String _id, long _duration) {
        try {

            // write metadata to file
            File file = new File(Config.INDEXING_PERFORMANCE_ARCHIVE);
            FileUtils.writeStringToFile(file, _id + "," + _duration + System.getProperty("line.separator"), StandardCharsets.UTF_8, true);

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

    public static boolean isAlreadyReceived(String _photoId) {
        boolean isReceived = false;
        if (new File(Config.METADATA_ARCHIVE_FILEPATH + System.getProperty("file.separator") + _photoId).exists()) {
            isReceived = true;
        }
        return isReceived;
    }

    public static String getWorkingDirectory() {
        return "" + System.getProperty("user.dir");
    }

    public static void copyFile(File _src, File _dest) {
        FileChannel source = null;
        FileChannel destination = null;
        try {
            if (!_dest.exists()) {
                _dest.createNewFile();
            }
            source = new FileInputStream(_src).getChannel();
            destination = new FileOutputStream(_dest).getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException ioex) {
                    ioex.printStackTrace(System.err);
                }
            }
            if (destination != null) {
                try {
                    destination.close();
                } catch (IOException ioex) {
                    ioex.printStackTrace(System.err);
                }
            }
        }
    }
    
    public static boolean delete(File _f) {
        return _f.delete();
    }

    public static String removeNewlines(String _val) {
        String retVal = "";
        retVal = _val;
        retVal = StringUtils.replace(retVal, System.getProperty("line.separator"), "");
        retVal = StringUtils.replace(retVal, "\n", "");
        return retVal;
    }
    
    public static String readFileToString(String _filepath) {
        String retVal = "";
        try {
            FileReader fr = new FileReader(_filepath);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                retVal += line;
                retVal += System.getProperty("line.separator");
            }
            br.close();
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }
        return retVal;
    }    
    
    public void list(File _file) {
        File[] children = _file.listFiles();
        for (File child : children) {
            list(child);
        }
    }
    
    public int getFileCount(String _filepath, boolean _recursive) {
        return getFiles(_filepath, _recursive).size();
    }
    
    public static List getFiles(String _filepath, boolean _recursive) {
        List files = new ArrayList();
        File dir = new File(_filepath);
        Collection collection = null;
        if (dir.exists()) {
            collection = FileUtils.listFiles(dir, null, _recursive);
        }
        
        File file = null;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            file = (File)it.next();
            files.add(file.getAbsolutePath());
        }
        return files;
    }    

}
