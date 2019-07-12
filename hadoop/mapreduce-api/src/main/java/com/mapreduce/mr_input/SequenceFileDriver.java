package com.mapreduce.mr_input;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;
import java.util.Date;

/**
 * $功能描述： SequenceFileDriver
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 20:20 v1.0
 */
public class SequenceFileDriver  {



    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\mr\\input\\in",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\mr\\input\\out" +new Date().getMinutes()
        };

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 引入class
        job.setJarByClass(SequenceFileDriver.class);
        job.setMapperClass(SequenceFileMapper.class);
        job.setReducerClass(SequenceFileReduce.class);

        // 输入输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        // 设置自定义的inputFormat
        job.setInputFormatClass(MyInputFormat.class);
        // 设置输出的二进制
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        // 输入输出路径
        // 定义输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));


        job.waitForCompletion(true);


    }
}
