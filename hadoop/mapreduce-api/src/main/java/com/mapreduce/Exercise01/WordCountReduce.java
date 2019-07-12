package com.mapreduce.Exercise01;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： WordCountReduce
 *
 * @author ：smart-dxw
 * @version ： 2019年6月11日 00:41:08 v1.0
 */
public class WordCountReduce extends Reducer<ClassBean, Text,Text , ClassBean> {

    @Override
    protected void reduce(ClassBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // 输出int => IntWritable
        context.write(null, key);
    }
}
