package com.mr_output;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;
import java.util.Date;

/**
 * $功能描述： OutPutFormatDriver
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 21:41 v1.0
 */
public class OutPutFormatDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\mr\\ouput\\in\\customize.txt",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\mr\\ouput\\out" +new Date().getMinutes()
        };

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 引入class
        job.setJarByClass(OutPutFormatDriver.class);
        job.setMapperClass(OutPutFormatMapper.class);
        job.setReducerClass(OutPutFormatReduce.class);


        // 输入输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 将自定义输出的格式组件设置到job中
        job.setOutputFormatClass(MyOutputFormat.class);

        // 输入输出路径
        // 定义输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));



        job.waitForCompletion(true);


    }
}
