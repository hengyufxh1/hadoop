package com.mapreduce.studentExam.classscore;

/**
 * $功能描述： AvgscoreMapper
 *
 * @author ：smart-dxw
 * @version ： 2019/6/19 21:58 v1.0
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AvgClassMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] s = line.split(",");
        // 去重班级 拼接 姓名与成绩在reduce阶段进行过滤
        context.write(new Text(s[0]), new Text(s[1] + "," + s[2]));
    }
}
