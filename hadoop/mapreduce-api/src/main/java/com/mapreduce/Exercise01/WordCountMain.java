package com.mapreduce.Exercise01;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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
 * @version ： 2019年6月11日 00:45:18 v1.0
 */
public class WordCountMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06102331\\in",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06102331\\out" +new Date().getMinutes()
        };
        // 获取配置文件
        Configuration conf = new Configuration();
        // 创建job任务
        Job job = Job.getInstance(conf);
        job.setJarByClass(WordCountMain.class);

        // 指定Map类和map的输出类型 Text，IntWritable
        job.setMapperClass(WordCountMap.class);
        job.setMapOutputKeyClass(ClassBean.class);
        job.setMapOutputValueClass(Text.class);

        // 设置分区
        job.setPartitionerClass(WordCountPartition.class);
        job.setNumReduceTasks(3);

        // 设置预合并 combine
//        job.setCombinerClass(WordCountCombine.class);

//        // 切片 如果不设置InputFormat，它默认用的是TextInputFormat.class
//        job.setInputFormatClass(CombineTextInputFormat.class);
//        CombineTextInputFormat.setMaxInputSplitSize(job, 4*1024*1024); // 4M
//        CombineTextInputFormat.setMinInputSplitSize(job, 2*1024*1024); // 2M

        // 指定Reducer类和reduce的输出数据类型Text，IntWritable
        job.setReducerClass(WordCountReduce.class);
//        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ClassBean.class);


        // 指定数据输入的路径和输出的路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 提交任务
        job.waitForCompletion(true);

    }
}
