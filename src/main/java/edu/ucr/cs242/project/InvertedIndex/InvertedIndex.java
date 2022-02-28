package edu.ucr.cs242.project.InvertedIndex;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InvertedIndex {

    public static class TokenizerMapper
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

            String DocID = (String) jsonObject.get("id");
            ID.set(DocID);


            for (Object myKey : jsonObject.keySet()) {
                String keyStr = (String) myKey;
                Object keyValue = jsonObject.get(keyStr);

                if (keyValue instanceof JSONObject) {

                    context.write(new Text(keyValue.toString()), new Text(ID));

                } else if (keyValue instanceof JSONArray myArray) {

                    for(int i = 0; i < myArray.size(); i++){

                        context.write(new Text(myArray.get(i).toString()), new Text(ID));
                    }
                } else if (keyValue != null) {
                     context.write(new Text(keyValue.toString()), new Text(ID));

                }
            }
        }

/*        public ArrayList <String> JSONHelper (JSONObject jobject){

            ArrayList <String> pair = new ArrayList<>();

            for (Object myKey : jobject.keySet()) {
                String keyStr = (String) myKey;
                Object keyValue = jobject.get(keyStr);

                if (keyValue instanceof JSONObject) {

                    JSONHelper((JSONObject) keyValue);

                } else if (keyValue instanceof JSONArray myArray) {

                    for(int i = 0; i < myArray.size(); i++){

                        JSONHelper((JSONObject) myArray.get(i));
                    }
                } else if (keyValue != null) {
                    pair.add(keyStr);
                    pair.add(keyValue.toString());
                }
            }
            return pair;
        }*/
    }

    public static class IntSumReducer
            extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {

            HashMap<String,Integer> map = new HashMap<String,Integer>();
      /*
      Iterable through all the values available with a key [word] and add them together and give the
      final result as the key and sum of its values along with the DocID.
      */
            for (Text val : values) {
                if (map.containsKey(val.toString())) {
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

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(InvertedIndex.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/JSON_TEST_FILE"));
        FileOutputFormat.setOutputPath(job, new Path("C:/Users/MachOne/Desktop/CS242/mapReduce_Index"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
