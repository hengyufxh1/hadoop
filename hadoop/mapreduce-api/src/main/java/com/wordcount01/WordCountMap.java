package com.wordcount01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： WordCountMap
 *
 * @author ：smart-dxw
 * @version ： 2019/6/9 22:12 v1.0
 */
public class WordCountMap extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException{
        // 拿到数据，进行数据类型转换 Text => String
        String line = value.toString();
        // 按照空格切分
        String [] split = line.split(" ");
        // 输出数据 KEYOUT, VALUEOUT
        for(String s : split){
            // 数据转换String = > Text   int=> IntWritable
            context.write(new Text(s), new IntWritable(1));
        }
    }
}
