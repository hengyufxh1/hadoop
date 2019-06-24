package com.friends.mr1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * $功能描述： ReduceFriend
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 21:44 v1.0
 */
public class ReduceFriend extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        // 将 所有的person以，拼接为value
        StringBuilder sb = new StringBuilder();

        for (Text t : values) {
            sb.append(",").append(t);
        }
        context.write(key, new Text(sb.toString().substring(1)));
    }
}

