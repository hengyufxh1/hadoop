package com.logclean;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： LogMapper
 *
 * @author ：smart-dxw
 * @version ： 2019/6/12 20:48 v1.0
 */
public class LogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 拿到数据
        String line = value.toString();

        // 解析日志
        boolean log = parseLog(line,context);

        // 日志是不合法的
        if(log){
            context.write(new Text(line), NullWritable.get());
        }else{
            return;
        }

        // 输出
    }

    private boolean parseLog(String line, Context context) {
        String[] fields = line.split(" ");
        if(fields.length>11){
            context.getCounter("map","合法数据").increment(1);
            return  true;
        }else{
            context.getCounter("map","非法数据").increment(1);
            return  false;
        }
    }
}
