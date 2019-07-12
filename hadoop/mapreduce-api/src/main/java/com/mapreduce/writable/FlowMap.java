package com.mapreduce.writable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： FlowMap
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:29 v1.0
 */
public class FlowMap extends Mapper<LongWritable, Text, Text, FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取数据
        String line = value.toString();
        // 切分
        String[] split = line.split("\t");

        // 上行 根据 数据 从后往前数
        long upFlow = Long.parseLong(split[split.length-3]);
        // 下行
        long downFlow = Long.parseLong(split[split.length-2]);
        context.write(new Text(split[1]), new FlowBean(upFlow,downFlow));
    }
}
