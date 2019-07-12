package com.mapreduce.wordcount01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Date;

/**
 * $功能描述： WordCountMain
 *
 * @author ：smart-dxw
 * @version ： 2019/6/9 22:47 v1.0
 */
public class WordCountMain {



    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06092342\\in",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06092342\\out" +new Date().getMinutes()
        };

        // 获取配置文件
        Configuration conf = new Configuration();

        // 创建job任务
        Job job = Job.getInstance(conf);
        // 指定Map类和map的输出类型 Text，IntWritable
        job.setMapperClass(WordCountMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 指定Reducer类和reduce的输出数据类型Text，IntWritable
        job.setReducerClass(WordCountReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 指定数据输入的路径和输出的路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[0]));

        // 提交任务
        job.waitForCompletion(true);

    }
}
