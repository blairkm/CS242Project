package edu.ucr.cs242.project.InvertedIndex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

public class InvertedIndex {

    public class JSONMapper
        extends Mapper<Object, Text, Text, Text>{

        private Text word = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, ParseException, org.json.simple.parser.ParseException {
            JSONParser parser = new JSONParser();

            Object obj = parser.parse(value.toString());
            JSONObject jsonObject = (JSONObject) obj;

            String DocID = (String) jsonObject.get("id");

            for (Object mykey: jsonObject.keySet()){
                Text mapValue = new Text();
                if(jsonObject.get(mykey) != null){
                    mapValue.set(jsonObject.get(mykey).toString());
                }
                try {
                    context.write(new Text(DocID), new Text(mapValue));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class myReducer
        extends Reducer<Text, Text, Text, Text>{

        public void myreduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            HashMap<String,Integer> map = new HashMap<String,Integer>();

            for(Text val: values){
                if(map.containsKey(val.toString())){
                    map.put(val.toString(), map.get(val.toString()) + 1);
                } else {
                    map.put(val.toString(), 1);
                }
            }
            StringBuilder docValueList = new StringBuilder();
            for(String docID : map.keySet()){
                docValueList.append(docID + ":" + map.get(docID) + " ");
            }
            context.write(key, new Text(docValueList.toString()));
        }
    }

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "inverted index");
        job.setJarByClass(InvertedIndex.class);
        job.setMapperClass(JSONMapper.class);

        job.setReducerClass(myReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/JSON_TEST_FILE"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/mapReduce_Index"));
        boolean success = job.waitForCompletion(true);
        System.exit(success ? 0 : 1);

    }


        }
