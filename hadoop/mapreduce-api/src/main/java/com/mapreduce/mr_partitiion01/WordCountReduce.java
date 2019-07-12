package com.mapreduce.mr_partitiion01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： WordCountReduce
 *
 * @author ：smart-dxw
 * @version ： 2019/6/9 22:30 v1.0
 */
public class WordCountReduce extends Reducer<Text, IntWritable,Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // 初始化一个计数器
        int count = 0;

        // 开始计数
        for(IntWritable value:values){
            count = count+value.get();
        }

        // 输出int => IntWritable
        context.write(key,new IntWritable(count));
    }
}
