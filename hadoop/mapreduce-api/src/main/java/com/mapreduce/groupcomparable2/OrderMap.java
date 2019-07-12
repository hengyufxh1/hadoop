package com.mapreduce.groupcomparable2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： OrderMap
 *
 * @author ：smart-dxw
 * @version ： 2019/6/16 21:29 v1.0
 */
public class OrderMap extends Mapper<LongWritable, Text, OrderBean, Text> {


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取数据
        String line = value.toString();
        // 切分
        String[] split = line.split("\t");

        // 赋值 数据封装
        OrderBean flowBean = new OrderBean();

        flowBean.setId(Integer.parseInt(split[0]));
        flowBean.setPrice(Double.parseDouble(split[2]));


        context.write(flowBean, new Text(split[1]));
    }

}
