package edu.ucr.cs242.project;

import edu.ucr.cs242.project.util.StringUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rehmke
 */
public class PostProcessData {
    
    public static void main(String[] args) {
        perform();        
    }
    
    private static void perform() {
        
        new File(Config.INDEXABLE_PROCESSED).mkdirs();
        
        //File dest = new File(INDEXABLE_PROCESSED);
        
        List files = FileUtil.getFiles(Config.INDEXABLE_ARCHIVE_FILEPATH, false);
        String currentFilepath = "";
        String fileContent = "";
        Iterator it = files.iterator();        
        while (it.hasNext()) {
            currentFilepath = ""+it.next();
            File currentFile = new File(currentFilepath);
            fileContent = FileUtil.readFileToString(currentFilepath);
            FileUtil.persistIndexableProcessed(StringUtils.replace(currentFile.getName(), "_", ""), StringUtil.scrub(fileContent));
        }      
        
    }
    
}