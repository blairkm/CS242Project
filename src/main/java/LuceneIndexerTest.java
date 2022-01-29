package main.java;

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rehmke
 */
public class LuceneIndexerTest {
 
    public static void main(String[] args) {        
        try {
            String INDEX_PATH = "/Users/rehmke/development/school/Cs242/DATA/index";
            String  JSON_FILE_PATH = "/Users/rehmke/development/school/Cs242/resource/response-example.json";
            LuceneIndexer Indexer = new LuceneIndexer(INDEX_PATH, JSON_FILE_PATH);
            Indexer.createIndex();                    
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        } catch (ParseException pex) {
            pex.printStackTrace(System.err);
        }
    }
    
}