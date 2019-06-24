package com.studentExam.avgscore;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * $功能描述： AvgReducer
 *
 * @author ：smart-dxw
 * @version ： 2019/6/19 22:01 v1.0
 */
public class AvgReducer extends Reducer<Text, IntWritable, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException {
        // 接收 成绩
        List<Integer> scores = new ArrayList<Integer>();
        // reduce阶段获取成绩
        Iterator<IntWritable> it = value.iterator();
        while (it.hasNext()) {
            scores.add(it.next().get());
        }

        // 获取集合的元素数量 总和 最小 平均 最大
//      IntSummaryStatistics{count=3, sum=237, min=66, average=79.000000, max=89}
        IntSummaryStatistics score = scores.stream().collect(Collectors.summarizingInt(Integer::intValue));
        // 输出 Math.round() 方法返回一个最接近的 int、long 型值，四舍五入。
        context.write(key, new Text(score.getMax() + "\t" + score.getMin() + "\t" + Math.round(score.getAverage())));
    }
}
