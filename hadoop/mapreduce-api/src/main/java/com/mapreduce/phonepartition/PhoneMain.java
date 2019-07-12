package com.mapreduce.phonepartition;

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
 * $功能描述： PhoneMain
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 21:41 v1.0
 */
public class PhoneMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06142151\\in",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06142151\\out" + new Date().getTime()
        };

        // 创建连接
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(PhoneMain.class);

        // 关联map和reduce
        job.setMapperClass(PhoneMap.class);
        job.setReducerClass(PhoneRed.class);

        job.setPartitionerClass(PhonePartition.class);

        // 关联map的输出类型和reduce输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置分区数
        job.setNumReduceTasks(4);

        // 定义输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        job.waitForCompletion(true);

    }
}
