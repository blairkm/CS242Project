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

public class IDFCounter {

    public static class IDFMapper
            extends Mapper<Object, Text, Text, Text> {

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {

            String [] line = value.toString().split("\\t");

            // pass through mapper
            context.write(new Text(line[0]), new Text(line[1]));


        }
    }

    public static class IDFReducer
            extends Reducer<Text, Text, Text, Text> {

        // must make dynamic docnumber later
        private static double docNumber;

        public void setup(Reducer.Context context) {
            docNumber = Double.parseDouble(context.getConfiguration().get("docNumber"));
        }

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {

            int docCounter = 0;
            for (Text ignored : values) {
                docCounter += 1;
            }

            double idf = Math.log((1 + docNumber) / (1 + docCounter)) + 1;

            context.write(key, new Text(String.valueOf(idf) + ":"+ String.valueOf(docCounter)));
            // word:id - idf:tf
        }
    }

/*    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "IDF Evaluator");
        job.setJarByClass(IDFCounter.class);
        job.setMapperClass(IDFMapper.class);
        job.setReducerClass(IDFReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/wordcountertest"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/IDFCounter_Test"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }*/

    public static boolean run(Configuration conf, boolean verbose) throws IOException, InterruptedException, ClassNotFoundException {
        Job job = Job.getInstance(conf, "IDF Evaluator");
        job.setJarByClass(IDFCounter.class);
        job.setMapperClass(IDFMapper.class);
        job.setReducerClass(IDFReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/WordCounter_Index/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/IDF_INDEX"));

        return job.waitForCompletion(verbose);

    }
}
