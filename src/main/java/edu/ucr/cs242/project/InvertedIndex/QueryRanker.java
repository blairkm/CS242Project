package edu.ucr.cs242.project.InvertedIndex;

import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.util.Scanner;

public class QueryRanker {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a location: ");
        String query = sc.nextLine();

        Configuration conf = new Configuration();
        conf.set("query", query);

        boolean verbose = false;

        Query.run(conf, verbose);
        Ranker.run(conf, verbose);
    }
}
