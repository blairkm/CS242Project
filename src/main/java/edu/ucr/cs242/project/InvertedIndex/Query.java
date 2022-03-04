package edu.ucr.cs242.project.InvertedIndex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

public class Query {


    public static class QueryMapper
            extends Mapper<Object, Text, Text, Text> {


        private static String query;

        public void setup(Context context) {
            query = context.getConfiguration().get("query");
        }

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {

            String [] line = value.toString().split("\\t");

            String [] wordDocID = line[0].split(":");

            String word = String.valueOf(wordDocID[0]);
            String DocID = String.valueOf(wordDocID[1]);

            String [] idftf = line[1].split(":");
            double idf = Double.parseDouble(idftf[0]);
            double tf = Double.parseDouble(idftf[1]);

            double k = 1.2;
            double b = 0.75;

            double relevance = 0.0;

           if(query.toLowerCase().contains(word.toLowerCase())) {

                // L = 1 because average doc length == doc length
                relevance = (idf*(k+1)*tf) /(k * (1.0-b+b*1)+tf);
               context.write(new Text(DocID), new Text(String.valueOf(relevance)));
            }
        }
    }

    public static class QueryReducer
            extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {

            double countValue = 0;

            for (Text value : values) {

                double score = Double.parseDouble(String.valueOf(value));

                countValue += score;
            }

            context.write(key, new Text(String.valueOf(countValue))); // word - idf - word_id

        }
    }

/*    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Query Evaluator");
        job.setJarByClass(Query.class);
        job.setMapperClass(QueryMapper.class);
        job.setReducerClass(QueryReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/IDFCounter_Test/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/Query_Test"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }*/

    public static boolean run(Configuration conf, boolean verbose) throws IOException, InterruptedException, ClassNotFoundException {
        Job job = Job.getInstance(conf, "Query Evaluator");
        job.setJarByClass(Query.class);
        job.setMapperClass(QueryMapper.class);
        job.setReducerClass(QueryReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/IDFCounter_Test/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/Query_Test"));

        return job.waitForCompletion(verbose);

    }
}
