package com.mapreduce.phonepartition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： PhoneMap
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 21:33 v1.0
 */
public class PhoneMap extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取输出
        String s = value.toString();

        // 切分数据
        System.out.println(s);
        String[] ss = s.split("\t");

        // 循环输出
        for (String fileds : ss) {
            context.write(new Text(fileds), NullWritable.get());
        }
    }
}