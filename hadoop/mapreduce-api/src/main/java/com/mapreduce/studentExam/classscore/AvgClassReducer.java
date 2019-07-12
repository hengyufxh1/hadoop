package com.mapreduce.studentExam.classscore;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * $功能描述： AvgReducer
 *
 * @author ：smart-dxw
 * @version ： 2019/6/19 22:01 v1.0
 */
public class AvgClassReducer extends Reducer<Text, Text, Text, Text> {

    Text k = new Text();
    Text v = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> value, Context context) throws IOException, InterruptedException {
        // 甲 乙 丙 人员
        String jiaStr = "";
        String yiStr = "";
        String bingStr = "";

        // reduce阶段获取人员
        Iterator<Text> text = value.iterator();
        while (text.hasNext()) {
            String[] split = text.next().toString().split(",");
            int score = Integer.parseInt(split[1]);
            // 甲级（90及以上）乙级（80到89）丙级（0到79）
            if (score >= 90) {
                jiaStr += ", " + split[0];
            } else if (score >= 80 && score <= 89) {
                yiStr += ", " + split[0];
            } else if (score <= 70) {
                bingStr += ", " + split[0];
            }
        }
        // 过滤掉 ", "
        jiaStr = jiaStr.substring(2);
        yiStr = yiStr.substring(2);
        bingStr = bingStr.substring(2);
        // 封装key
        k.set("课程" + key.toString() + ":\n");
        // 封装value
        String s = "高:\t" + jiaStr + "\t总人数:" + jiaStr.split(", ").length + "人\n"
                + "\t中:\t" + yiStr + "\t总人数:" + yiStr.split(", ").length + "人\n"
                + "\t低:\t" + bingStr + "\t总人数:" + bingStr.split(", ").length + "人\n";
        v.set(s);
        context.write(k, v);
    }
}
