package com.mapreduce.writablesort2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： FlowMap
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:29 v1.0
 */
public class FlowMap extends Mapper<LongWritable, Text, FlowBean, NullWritable> {



    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取数据
        String line = value.toString();
        // 切分
        String[] split = line.split("\t");

        // 赋值 数据封装
        FlowBean flowBean = new FlowBean();

        flowBean.setId(Integer.parseInt(split[0]));
        flowBean.setPrice(Double.parseDouble(split[2]));


        context.write(flowBean, NullWritable.get());
    }
}
