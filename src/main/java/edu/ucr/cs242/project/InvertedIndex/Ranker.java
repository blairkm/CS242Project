package edu.ucr.cs242.project.InvertedIndex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Ranker {


    public static class RankMapper
            extends Mapper<Object, Text, Text, DoubleWritable> {


        private TreeMap<String, Double> tmap;

        public void setup(Context context) {

            tmap = new TreeMap<String, Double>();
        }

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {

            String [] line = value.toString().split("\t");
            String DocID = line[0];
            double score = Double.parseDouble(line[1]);

            tmap.put(DocID, score);

            // remove first key-value if size increases beyond 10
            if(tmap.size() > 10) {
                tmap.remove(tmap.firstKey());
            }
        }

        @Override
        public void cleanup(Context context) throws IOException,
                InterruptedException
        {
            for (Map.Entry<String, Double> entry : tmap.entrySet())
            {

                String myDocID = entry.getKey();
                double myScore = entry.getValue();


                context.write(new Text(myDocID), new DoubleWritable(myScore));
            }
        }
    }

    public static class RankReducer
            extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

        private TreeMap<String, Double> tmap2;

        @Override
        public void setup(Context context) throws IOException,
                InterruptedException
        {
            tmap2 = new TreeMap<String, Double>();
        }


        public void reduce(Text key, Iterable<DoubleWritable> values,
                           Context context
        ) throws IOException, InterruptedException {

            String DocID = key.toString();
            double score = 0;

            for (DoubleWritable value : values){
                score = value.get();
            }

            tmap2.put(DocID, score);

            if (tmap2.size() > 10) {
                tmap2.remove(tmap2.firstKey());
            }
        }

        @Override
        public void cleanup(Context context) throws IOException,
                InterruptedException
        {
            for (Map.Entry<String, Double> entry : tmap2.entrySet())
            {

                String myDocID = entry.getKey();
                double myScore = entry.getValue();


                context.write(new Text(myDocID), new DoubleWritable(myScore));
            }
        }

    }

/*    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Rank Evaluator");
        job.setJarByClass(Query.class);
        job.setMapperClass(RankMapper.class);
        job.setReducerClass(RankReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/Query_Test/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/Rank_Test"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }*/

    public static boolean run(Configuration conf, boolean verbose) throws IOException, InterruptedException, ClassNotFoundException {
        Job job = Job.getInstance(conf, "Rank Evaluator");
        job.setJarByClass(Query.class);
        job.setMapperClass(RankMapper.class);
        job.setReducerClass(RankReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/Query_Test/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/Rank_Test"));

        return job.waitForCompletion(verbose);

    }
}
