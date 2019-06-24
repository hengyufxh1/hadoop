package com.join.reducejoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Date;

/**
 * $功能描述： Driver
 *
 * @author ：smart-dxw
 * @version ： 2019/6/17 20:12 v1.0
 */
public class Run {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\join\\01\\in"
                ,"E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\join\\01\\out"+new Date().getMinutes()
        };


        // 创建连接
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(Run.class);

        // 关联map和reduce
        job.setMapperClass(JoinMap.class);
        job.setReducerClass(JoinReduce.class);


        // 关联map的输出类型和reduce输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);

        job.setOutputKeyClass(TableBean.class);
        job.setOutputValueClass(NullWritable.class);


        // 定义输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));


        job.waitForCompletion(true);
        System.out.println("success...");

    }
}
