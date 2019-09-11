package com.flink

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * 功能描述 ：ScalaWordCount
  *
  * @author ：smart-dxw
  * @version ：2019/9/11 22:45 v1.0
  */
object ScalaWordCount {
  def main(args: Array[String]): Unit = {


    // get the execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment


    // get input data by connecting to the socket
    val text = env.socketTextStream("11.11.11.151", 9000, '\n')


    // parse the data, group it, window it, and aggregate the counts
    val windowCounts = text
      .flatMap { w => w.split(" ") }
      .map { w => WordWithCount(w, 1) }
      .keyBy("word")
      .timeWindow(Time.seconds(5), Time.seconds(1))
      .sum("count")


    // print the results with a single thread, rather than in parallel
    windowCounts.print().setParallelism(1)


    env.execute("Socket Window WordCount")
  }


  // Data type for words with count
  case class WordWithCount(word: String, count: Long)


}