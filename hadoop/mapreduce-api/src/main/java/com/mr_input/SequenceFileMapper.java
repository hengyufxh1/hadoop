package com.mr_input;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * $功能描述： SequenceFileMapper
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 20:38 v1.0
 */
public class SequenceFileMapper extends Mapper<NullWritable, BytesWritable, Text, BytesWritable> {
    Text k = new Text();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获取文件的路径
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String name = fileSplit.getPath().toString();
        k.set(name);
    }

    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        context.write(k,value);
    }
}