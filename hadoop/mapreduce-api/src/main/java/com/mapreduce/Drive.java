package com.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * $功能描述： Drive
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 20:22 v1.0
 */
public class Drive {

    /**
     * @param object        run运行类
     * @param mymap         map类
     * @param mymapkey      mapkey
     * @param mymapvalue    mapvalue
     * @param myreduce      reduce类
     * @param myreducekey   reducekey
     * @param myreducevalue reducevalue
     * @param args1         路径1
     * @param args2         路径2
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public static void run(Class<?> object, Class<? extends Mapper> mymap, Class<?> mymapkey, Class<?> mymapvalue, Class<? extends Reducer> myreduce, Class<?> myreducekey, Class<?> myreducevalue, String args1, String args2) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {

        // 1 获取job信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 加载jar包
        job.setJarByClass(object);

        // 3 关联map和reduce
        job.setMapperClass(mymap);
        // 4 设置最终输出类型
        job.setMapOutputKeyClass(mymapkey);
        job.setMapOutputValueClass(mymapvalue);

        // 设置reduce
        job.setReducerClass(myreduce);
        job.setOutputKeyClass(myreducekey);
        job.setOutputValueClass(myreducevalue);

        //判断输出路径是否存在
        Path path = new Path(args2);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }

        // 5 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args1));
        FileOutputFormat.setOutputPath(job, new Path(args2));

        // 6 提交
        job.waitForCompletion(true);
    }
}
