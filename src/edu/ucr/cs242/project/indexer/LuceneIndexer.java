package edu.ucr.cs242.project.indexer;

import edu.ucr.cs242.project.Config;
import edu.ucr.cs242.project.FileUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import org.apache.lucene.store.FSDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class LuceneIndexer {

    String indexPath;
    String jsonFilePath;
    IndexWriter indexWriter;

    /* create LuceneIndexer object */
    public LuceneIndexer(String indexPath, String jsonFilePath){
        this.indexPath = indexPath;
        this.jsonFilePath = jsonFilePath;
    }
    
    public static void perform(String _jsonFilepath) {
        try {
            LuceneIndexer Indexer = new LuceneIndexer(Config.INDEXED_ARCHIVE_FILEPATH, _jsonFilepath);
            Indexer.createIndex();                    
        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        } catch (ParseException pex) {
            pex.printStackTrace(System.err);
        }        
    }

    /* Parse the JSON file */
    public JSONArray JSONFileParse() throws IOException, ParseException {

        // get JSON file
        FileReader reader = new FileReader(jsonFilePath);

        //Parse JSON file
        JSONParser parser = new JSONParser();
        Object fileObjects = parser.parse(reader);
        JSONArray arrayObjects= new JSONArray();
        arrayObjects.add(fileObjects);

        return arrayObjects;
    }

    /* index opener */
    public void openIndex(){
        try{
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            // @note: Unsure if OpenMode.CREATE_OR_APPEND is appropriate here, so checking manually...
            if (FileUtil.isDirectoryEmpty(Config.INDEXED_ARCHIVE_FILEPATH)) {
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            } else {
                iwc.setOpenMode(IndexWriterConfig.OpenMode.APPEND);    
            }
            
            indexWriter = new IndexWriter(dir, iwc);

        } catch (Exception e){
            System.err.println("Error opening the index. " + e.getMessage());
        }
    }

    public void addDocuments(JSONArray jsonObjects){
        for(JSONObject object : (List<JSONObject>) jsonObjects){

            // create new document
            Document doc = new Document();

            // for each field, add as a stringField to the document
            
            for(String field : (Set<String>) object.keySet()){
                System.out.println(field);
                try {
                    Class type = object.get(field).getClass();
                    if(type.equals(String.class)){
                        doc.add(new StringField(field, (String)object.get(field), Field.Store.YES));
                    }
                    else if(type.equals(Boolean.class) | type.equals(Long.class) | type.equals(Double.class)){
                        doc.add(new StringField(field, object.get(field).toString(), Field.Store.YES));
                    }
                } catch (Exception ex) {
                    //ex.printStackTrace(System.err);
                }                
            }
            try{
                System.out.println(doc);
                indexWriter.addDocument(doc);
            } catch (IOException exception){
                System.err.println("Error adding documents to index." + exception.getMessage());
            }
        }
    }

    /* commit and close the index */
    public void finish(){
        try{
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e){
            System.err.println("A problem was encountered when closing the index: " + e.getMessage());
        }
    }

    /* create index */
    public void createIndex() throws IOException, ParseException {
        JSONArray jsonObjects = JSONFileParse();
        openIndex();
        addDocuments(jsonObjects);
        finish();
    }
}
