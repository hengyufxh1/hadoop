package com.mapreduce.join.reducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * $功能描述： JoinMap
 *
 * @author ：smart-dxw
 * @version ： 2019/6/17 20:12 v1.0
 */
public class JoinMap extends Mapper<LongWritable,Text, Text,TableBean> {

    TableBean tableBean = new TableBean();
    Text text = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取文件的路径
        FileSplit filesplit = (FileSplit) context.getInputSplit();
        // 每个文件的名字
        String name = filesplit.getPath().getName();
        // 获取数据
        String line = value.toString();
        // 判断， 根据文件的名字不同添加标记
        if(name.equals("order.txt")){
            String[] split = line.split("\t");
            // 封装
            tableBean.setOrder_id(split[0]);
            tableBean.setP_id(split[0]);
            tableBean.setAmount(Integer.parseInt(split[0]));
            tableBean.setFlag("0");
            tableBean.setPname("");

            text.set(split[1]);
        }else {
            //
            String[] split = line.split("\t");
            // 封装
            tableBean.setP_id(split[0]);
            tableBean.setPname(split[1]);
            tableBean.setFlag("1");
            tableBean.setOrder_id("");
            tableBean.setAmount(0);

            text.set(split[0]);
        }
        context.write(text,tableBean);
    }
}
