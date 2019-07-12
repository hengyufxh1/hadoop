package com.mapreduce.mr_input;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： SequenceFileMapper
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 20:20 v1.0
 */
public class SequenceFileReduce extends Reducer<Text, BytesWritable, Text, BytesWritable> {
    @Override
    protected void reduce(Text key, Iterable<BytesWritable> values, Context context) throws IOException, InterruptedException {
        for (BytesWritable b : values) {
            context.write(key, b);
        }
    }
}
