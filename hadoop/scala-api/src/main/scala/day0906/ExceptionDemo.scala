package com.scala.day01

import java.io.FileNotFoundException

/**
  * $功能描述 ：Demo2
  *
  * @author ：smart-dxw
  * @version ：2019/8/7 21:35 v1.0
  */
object ExceptionDemo {

  def main(args: Array[String]): Unit = {
    /**
      *
      */

    try {
      println("————————try catch finally-----------")
      val words = scala.io.Source.fromFile("E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\06102331\\in\\class.txt").mkString
      println(words)

    } catch {
      case ex: FileNotFoundException => {
        println("FileNotFoundException")
      }
      case ex: IllegalArgumentException => {
        println("IllegalArgumentException")
      }
      case _ : Exception => {
        println("Other Exception")
      }
    } finally {
      println("This is finally")

    }
  }


}
