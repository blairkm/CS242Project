package edu.ucr.cs242.project.InvertedIndex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.kerby.config.Conf;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WordCounter {

    public static class WordCountMapper
            extends Mapper<Object, Text, Text, Text> {

        private final Text ID = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {

            JSONParser parser = new JSONParser();

            Object obj = null;
            try {
                obj = parser.parse(value.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = (JSONObject) obj;

            String DocID = (String) jsonObject.get("photoId");
            ID.set(DocID);


            for (Object myKey : jsonObject.keySet()) {
                String keyStr = (String) myKey;
                Object keyValue = jsonObject.get(keyStr);

                if (keyValue instanceof JSONObject) {

                    context.write(new Text(keyValue.toString() + ":" + ID), new Text("1"));

                } else if (keyValue instanceof JSONArray myArray) {

                    for(int i = 0; i < myArray.size(); i++){

                        context.write(new Text(myArray.get(i).toString()+ ":" + ID), new Text("1"));
                    }
                } else if (keyValue != null) {
                    context.write(new Text(keyValue.toString() + ":" + ID), new Text("1"));

                }
            }
        }
    }

    public static class WordCountReducer
            extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {

            int wordCounter = 0;

            for (Text ignored : values) {
                wordCounter += 1;
            }

            context.write(new Text(key), new Text(String.valueOf(wordCounter)));
        }
    }

/*    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCounter.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // added to make recursive, run on all files in folder

        FileInputFormat.setInputDirRecursive(job, true);
        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/test_data"));


        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/WordCounter_Test"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }*/

    public static boolean run(Configuration conf, String args, boolean verbose) throws IOException, InterruptedException, ClassNotFoundException {
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCounter.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // added to make recursive, run on all files in folder

        FileInputFormat.setInputDirRecursive(job, true);
        FileInputFormat.addInputPath(job, new Path(args));


        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/WordCounter_Index"));

        return job.waitForCompletion(verbose);

    }
}
