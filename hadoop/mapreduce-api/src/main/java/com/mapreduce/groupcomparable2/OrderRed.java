package com.mapreduce.groupcomparable2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： FlowRed
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:35 v1.0
 */
public class OrderRed extends Reducer<OrderBean, Text, OrderBean,Text > {

    @Override
    protected void reduce(OrderBean flowBean, Iterable<Text> values, Context context) throws IOException, InterruptedException {



        // 输出
        for(Text text :values){
            System.out.println(text.toString());
            context.write(flowBean, new Text(text));
        }
        System.out.println("----------------------");
    }
}
