package com.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * $功能描述： WordCountStreaming
 *
 * @author ：smart-dxw
 * @version ： 2019/9/11 22:24 v1.0
 */
public class WordCountStreaming {


    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> text = env.socketTextStream("11.11.11.151", 9000, '\n');


        // Function第一个参数是输入的 第二个参数是输出的
        DataStream<WordWithCount> windowCount =text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String s, Collector<WordWithCount> collector) throws Exception {
                for (String word : s.split(" ")) {
                    collector.collect(new WordWithCount(word,1L));
                }
            }
        }).keyBy("word")
                .timeWindow(Time.seconds(2),Time.seconds(1))
                .sum("count");

        windowCount.print();

        env.execute("streaming word count");


    }

    /**
     * 主要为了存储单词以及单词出现的次数
     */
    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordWithCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }

    }


}



