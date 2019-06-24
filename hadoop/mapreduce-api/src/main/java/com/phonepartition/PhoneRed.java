package com.phonepartition;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： PhoneReduce
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 21:36 v1.0
 */
public class PhoneRed extends Reducer<Text, NullWritable, Text, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(new Text(key), NullWritable.get());
    }
}
