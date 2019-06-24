package com.writablesort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： FlowRed
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:35 v1.0
 */
public class FlowRed extends Reducer<FlowBean, Text, Text, FlowBean> {

    @Override
    protected void reduce(FlowBean flowBean, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text text : values){
            // 输出
            context.write(text, flowBean);
        }
    }
}
