package com.mapreduce.friends.mr1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： MapFriend
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 21:44 v1.0
 */
public class MapFriend extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();
    Text v = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 获取数据 A:B,C,D,F,E,O
        String line = value.toString();
        //  切分：得到map  v preson
        String[] s1 = line.split(",");
        // map 输出
        v.set(s1[0]);
        String[] s2 = s1[1].split(",");

        for(String friend: s2){
            // 输出 《友 人》;
            k.set(friend);
            context.write(k,v);

        }


    }
}
