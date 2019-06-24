package com.writable;

import org.apache.hadoop.mapreduce.Reducer;

import javax.xml.soap.Text;
import java.io.IOException;

/**
 * $功能描述： FlowRed
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:35 v1.0
 */
public class FlowRed extends Reducer<Text, FlowBean,Text,FlowBean> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        // 初始化 两个计数器， 分别计算上行流量之和
        long sumUpFlow= 0;
        long sumDownFlow = 0;

        for (FlowBean f : values){
            sumUpFlow += f.getUpFlow();
            sumDownFlow += f.getDownFlow();
        }
        context.write(key,new FlowBean(sumUpFlow,sumDownFlow));
    }
}
