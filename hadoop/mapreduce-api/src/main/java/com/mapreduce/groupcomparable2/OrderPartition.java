package com.mapreduce.groupcomparable2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * $功能描述： OrderPartition
 *
 * @author ：smart-dxw
 * @version ： 2019/6/16 21:39 v1.0
 */
public class OrderPartition extends Partitioner<OrderBean, Text> {

    @Override
    public int getPartition(OrderBean orderBean, Text text, int i) {
        return (orderBean.getId() & 2147483647) % i;
    }
}
