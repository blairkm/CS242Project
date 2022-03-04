package edu.ucr.cs242.project.InvertedIndex;

import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

public class MapReduceIndexer {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Configuration conf = new Configuration();

        boolean verbose = false;
        String posArgs = "C:/Users/MachOne/Desktop/CS242/test_data";

        WordCounter.run(conf, posArgs, verbose);
        IDFCounter.run(conf, verbose);
    }
}
