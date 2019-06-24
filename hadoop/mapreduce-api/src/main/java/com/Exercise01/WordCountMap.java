package com.Exercise01;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： WordCountMap
 *
 * @author ：smart-dxw
 * @version ： 2019年6月11日 00:40:20 v1.0
 */
public class WordCountMap extends Mapper<LongWritable, Text, ClassBean, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException{

        ClassBean classBean = new ClassBean();
        // 拿到数据，进行数据类型转换 Text => String
        String line = value.toString();
        // 按\t格切分
        String [] split = line.split(" ");

        String name = split[0];
        String clas = split[1];
        classBean.setName(name);
        classBean.setClas(clas);

        context.write(classBean, new Text(clas));
    }
}
