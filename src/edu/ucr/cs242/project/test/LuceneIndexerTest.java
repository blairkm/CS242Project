package edu.ucr.cs242.project.test;

import edu.ucr.cs242.project.Config;
import edu.ucr.cs242.project.indexer.LuceneIndexer;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rehmke
 */
public class LuceneIndexerTest {
 
    public static void main(String[] args) {        
        try {
            String INDEX_PATH = Config.INDEXED_ARCHIVE_FILEPATH;
            String JSON_FILE_PATH = "/Users/rehmke/development/school/Cs242/resource/response-random-example.json";
            LuceneIndexer Indexer = new LuceneIndexer(INDEX_PATH, JSON_FILE_PATH);
            Indexer.createIndex();                    
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        } catch (ParseException pex) {
            pex.printStackTrace(System.err);
        }
    }
    
}