package com.mr_partitiion01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * $功能描述： WordCountPartition
 *
 * @author ：smart-dxw
 * @version ： 2019/6/10 22:11 v1.0
 */
public class WordCountPartition extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {

        // 拿到数据
        String t = text.toString();

        // 得到每个单词的长度
        int length = t.length();
        if(length%2==0){
            return 0;
        }else {
            return 1;
        }

    }
}
