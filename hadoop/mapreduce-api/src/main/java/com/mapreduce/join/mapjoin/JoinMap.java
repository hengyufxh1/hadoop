package com.mapreduce.join.mapjoin;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.HashMap;

/**
 * $功能描述： JoinMap
 *
 * @author ：smart-dxw
 * @version ： 2019/6/17 20:12 v1.0
 */
public class JoinMap extends Mapper<LongWritable, Text,Text, NullWritable> {

    HashMap hashMap = new HashMap<String, String>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String filepath = "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\join\\01\\in\\pd.txt";

        // 获取缓存的文件 bufferedReader
        BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)),"UTF-8"));

        // 一行一行读取数据
        String line;
        while (StringUtils.isNotEmpty(line=buf.readLine())){
            // 切分 01 小米
            String[] split = line.split("\t");
            // 数据存储进集合
            hashMap.put(split[0], split[1]);
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取 大表 （订单表）
        String line = value.toString();

        // 切分
        String[] fileds = line.split("\t");

        String pid = fileds[1];

        if(hashMap.containsKey(pid)){
            context.write(new Text(fileds[0]+"\t"+hashMap.get(pid)+"\t"+fileds[2]), NullWritable.get());

        }
    }
}
