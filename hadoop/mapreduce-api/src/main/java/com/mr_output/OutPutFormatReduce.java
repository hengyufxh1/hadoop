package com.mr_output;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： OutPutFormatReduce
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 21:42 v1.0
 */
public class OutPutFormatReduce extends Reducer<Text, NullWritable, Text, NullWritable> {

    Text k = new Text();

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        String s = key.toString();
        k.set(s + "\r\n");
        System.out.println(k.toString());
        context.write(k, NullWritable.get());
    }
}
