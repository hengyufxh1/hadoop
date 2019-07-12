package com.mapreduce.logclean1;

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
 * $功能描述： LogDriver
 *
 * @author ：smart-dxw
 * @version ： 2019/6/12 20:49 v1.0
 */
class LogMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\logclean\\in",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\logclean\\out" +new Date().getMinutes()
        };

        // 获取job信息
        Configuration conf = new Configuration();


        Job job = Job.getInstance(conf);

        // 加载jar包
        job.setJarByClass(LogMain.class);
        // 关联 map
        job.setMapperClass(LogMapper.class);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        // 设置最终除数类型
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 提交
        job.waitForCompletion(true);


        System.out.println("ok");


    }
}
