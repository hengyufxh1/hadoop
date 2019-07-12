package com.mapreduce.writablesort;

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
public class FlowMap extends Mapper<LongWritable, Text, FlowBean, Text> {



    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取数据
        String line = value.toString();
        // 切分
        String[] split = line.split("\t");

        // 赋值 数据封装
        FlowBean flowBean = new FlowBean();
        flowBean.setUpFlow(Long.parseLong(split[0]));
        flowBean.setDownFlow(Long.parseLong(split[1]));
        flowBean.setSumFlow(Long.parseLong(split[2]));



        context.write(flowBean, new Text(split[0]));
    }
}
