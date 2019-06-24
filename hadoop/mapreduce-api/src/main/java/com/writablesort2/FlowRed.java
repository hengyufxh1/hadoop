package com.writablesort2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： FlowRed
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:35 v1.0
 */
public class FlowRed extends Reducer<FlowBean, NullWritable, FlowBean,NullWritable > {

    @Override
    protected void reduce(FlowBean flowBean, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        // 输出
         context.write(flowBean, NullWritable.get());
    }
}
