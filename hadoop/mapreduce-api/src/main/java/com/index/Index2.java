package com.index;

import com.Drive;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * $功能描述： index1
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 20:17 v1.0
 */
public class Index2 {

    public static void main(String[] args) throws ClassNotFoundException, URISyntaxException, InterruptedException, IOException {
        args = new String[]{};

        Drive.run(Index2.class,
                IndexMap.class,
                Text.class,
                Text.class,
                IndexReduce.class,
                Text.class,
                Text.class,
                args[0],
                args[2]);


    }


    class IndexMap extends Mapper<LongWritable, Text, Text, Text> {
        Text k = new Text();
        Text v = new Text();
        String name;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit inputSplit = (FileSplit) context.getInputSplit();
            name = inputSplit.getPath().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();

            String[] split = line.split("--");

            k.set(split[0]);
            v.set(split[0]);
            context.write(k, v);
        }
    }


    class IndexReduce extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuffer sb = new StringBuffer();
            for (Text t : values) {
                // a.txt 3
                String[] split = t.toString().split("\t");
                // 重新按照 -->
                String s = split[0] + "-->" + split[1];
                sb.append(s).append("\t");
            }
            // 输出
            context.write(key, new Text(sb.toString()));
        }
    }
}
