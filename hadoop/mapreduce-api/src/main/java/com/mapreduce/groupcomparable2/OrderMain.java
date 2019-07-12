package com.mapreduce.groupcomparable2;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Date;

/**
 * $功能描述： FlowMain
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:40 v1.0
 */
public class OrderMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06162144\\in",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06162144\\out" +new Date().getTime()
        };

        // 创建连接
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(OrderMain.class);

        // 关联map和reduce
        job.setMapperClass(OrderMap.class);
        job.setReducerClass(OrderRed.class);


        // 关联map的输出类型和reduce输出类型
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(Text.class);


        // 指定分区 和分区数
        job.setPartitionerClass(OrderPartition.class);
        job.setNumReduceTasks(3);

        // 指定辅助排序
        job.setGroupingComparatorClass(GroupComparee.class);

        // 定义输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));


        job.waitForCompletion(true);
        System.out.println("success...");

    }
}
