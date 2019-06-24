package com.Exercise01;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * $功能描述： WordCountPartition
 *
 * @author ：smart-dxw
 * @version ： 2019年6月11日 00:40:01 v1.0
 */
public class WordCountPartition extends Partitioner<ClassBean, Text> {

    @Override
    public int getPartition(ClassBean classBean, Text text, int i) {
        // 拿到数据
        String t = text.toString();
        int num = 3;
        if("001".equals(t)){
            num = 0;
        }else if("002".equals(t)) {
            num = 1;
        }else if("003".equals(t)) {
            num = 2;
        }
        return num;
    }
}
