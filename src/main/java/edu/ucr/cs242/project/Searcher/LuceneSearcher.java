package edu.ucr.cs242.project.Searcher;

import edu.ucr.cs242.project.Config;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class LuceneSearcher {

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(Config.INDEXED_ARCHIVE_FILEPATH));
        IndexReader reader = DirectoryReader.open(dir);
        return new IndexSearcher(reader);
    }

    private static TopDocs mySearcher(String myQuery, IndexSearcher searcher) throws Exception {
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
                new String[]{"photoId", "created", "description", "altDescription", "urlRaw", "userId",
                "username", "userBio", "userFullname", "userLocation", "camera", "exposure", "aperture",
                "focal_length", "iso", "photoLocation", "photoCity", "photoCountry", "photoLatitude",
                "photoLongitude", "photoViews", "photoDownloads"},
                new StandardAnalyzer());

        Query query = queryParser.parse(myQuery);

        return searcher.search(query, 10);
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a location: ");
        String query = sc.nextLine();

        IndexSearcher searcher = createSearcher();
        TopDocs foundDocs = mySearcher(query, searcher);

        for(ScoreDoc scoreDoc : foundDocs.scoreDocs){
            Document d = searcher.doc(scoreDoc.doc);
            System.out.println(String.format(d.get("photoId")));
        }


    }
}
