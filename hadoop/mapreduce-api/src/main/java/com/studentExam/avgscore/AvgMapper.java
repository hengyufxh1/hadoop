package com.studentExam.avgscore;

/**
 * $功能描述： AvgscoreMapper
 *
 * @author ：smart-dxw
 * @version ： 2019/6/19 21:58 v1.0
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AvgMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 部曲
        String line = value.toString();
        String[] s = line.split(",");
        // 这里k根据姓名区分
        context.write(new Text(s[1]), new IntWritable(Integer.parseInt(s[2])));
    }
}
