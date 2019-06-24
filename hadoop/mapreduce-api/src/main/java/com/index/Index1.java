package com.index;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * $功能描述： Index1
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 20:17 v1.0
 */
public class Index1 {
    public static void main(String[] args) {
        args = new String[]{};
    }

    class IndexMap extends Mapper<LongWritable, Text, Text, IntWritable> {

        String name;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit inputSplit = (FileSplit) context.getInputSplit();
            name = inputSplit.getPath().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();

            String[] split = line.split(" ");



        }
    }


    class IndexReduce extends Reducer<Text, IntWritable, Text,Text>{

    }

}
