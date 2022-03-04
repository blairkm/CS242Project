package edu.ucr.cs242.project.InvertedIndex;

import edu.ucr.cs242.project.FileUtil;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

public class MapReduceIndexer {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Configuration conf = new Configuration();

        int fileCount = FileUtil.getFileCount("C:/Users/MachOne/Desktop/CS242/INDEXABLE_PROCESSED", true);

        conf.set("docNumber", String.valueOf(fileCount));

        boolean verbose = false;
        String posArgs = "C:/Users/MachOne/Desktop/CS242/INDEXABLE_PROCESSED";

        WordCounter.run(conf, posArgs, verbose);
        IDFCounter.run(conf, verbose);
    }
}
