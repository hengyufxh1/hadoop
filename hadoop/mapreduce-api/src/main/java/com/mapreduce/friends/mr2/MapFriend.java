package com.mapreduce.friends.mr2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

/**
 * $功能描述： MapFriend
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 21:48 v1.0
 */
public class MapFriend extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();
    Text v = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 获取数据 A(friend) I,K,C,B,G,F,H,O,D(person)
        String line = value.toString();

        String[] split1 = line.split("\t");
        // map 输出
        v.set(split1[0]);

        String[] split2 = split1[1].split(",");

        Arrays.sort(split2);

        for (int i = 0; i < split2.length; i++) {
            for (int j = i + 1; j < split2.length-1; j++) {
                k.set(split2[i] + "-" + split2[j]);
                context.write(k, v);
            }
        }
    }
}
