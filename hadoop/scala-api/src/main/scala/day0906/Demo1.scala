package com.scala.day01
/**
  * 功能描述 ：Demo1
  *
  * @author ：smart-dxw
  * @version ：2019/8/5 22:25 v1.0
  */

import scala.math._
import scala.util.control.Breaks._

object Demo1 {

  def main(args: Array[String]): Unit = {
    // for 循环
    // 定义一个集合
    var list = List("Mary", "Tom", "Mike")
    println("-----for循环的第一种写法-----")
    for (s <- list) println(s)

    // <- scala 中的提取符 把list中每个元素提取出来 赋给s


    println("-----for循环的第二种写法-----")
    // 打印出长度大于3的名字
    for {
      s <- list
      if (s.length > 3)
    } println(s)

    println("-----for循环的第三种写法-----")
    for (s <- list if s.length <= 3) println(s)

    println("-----for循环的第四种写法-----")
    // yield关键字，作用：产生一个新集合
    // 把list中 每个元素变成大写，返回一个新的集合

    var newList = for {
      s <- list
      s1 = s.toUpperCase()
    } yield (s1)
    for (s <- newList) println(s)


    println("--------------------------------------------------")
    println("-----while循环的写法-----")
    // 定义循环变量
    var i = 0
    while (i < list.length) {
      print(i + " --> " + list(i))
      i += 1
    }


    println("-----do while循环的写法-----")
    var j = 0
    do {
      println(list(j))
      j += 1
    } while (j < list.length)


    println("-----foreach循环的写法-----")
    list.foreach(println)


    /**
      * 判断101到200之间有多少个素数
      * 判断素数的方法：
      * 用一个数x分别取出2到sqrt(x)之间所有整数，如果可以被整除，则表明不是素数
      *
      * 程序实现方法
      * 定义两层循环
      * 第一层101-200之间
      * 第二层从2到sqrt(第一层)
      * 判断能否被整除
      * 16需要判断2到4，16能被2 3 4 整除
      */


    println("-----循环嵌套-----")
    // 保存素数个数
    var count: Int = 0
    //外层循环变量
    var index_outer = 0
    // 内层循环变量
    var index_inner = 0

    for (index_outer <- 101 until 200) {

      // 标识是否能被整除
      var b = false
      breakable {
        index_inner = 2
        while (index_inner <= sqrt(index_outer)) {
          // 判断是否是素数
          if (index_outer % index_inner == 0) {
            // 可以被整除 不是素数
            b = true
            break
          }
          index_inner += 1
        }
        if (!b) {
          count += 1
        }
      }
    }
    println("素数个位为：" + count)


    /**
      *
      * 冒泡排序
      *
      * 算法分析：
      * 1、比较相邻的元素，如果第一个比第二个打，就交换他们两个。
      * 2、对每一对相邻元素做同样的工作，从开始的第一对到结尾的最后一对。这步做完后，最后的元素回事最大的数
      * 3、针对所有元素，重复上面的步骤，排除已经排序好的元素。
      *
      * 程序分析：
      * 1、两层虚幻
      * 2、外层循环控制比较的次数
      * 3、内层循环控制到达的位置，也就是结束比较的位置
      *
      */
    println("-----冒泡排序-----")

    var a = Array(9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 91, 81, 71, 61, 51, 41, 31, 21, 11, 10)
    println("-----排序前-----")
    a.foreach(a => {
      print(a + ", ")
    })

    for (i <- 0 until a.length - 1) {
      for (j <- 0 until a.length - 1 - i) {
        // 元素大小判断并进行交换
        if (a(j) > a(j + 1)) {
          var tmp = a(j)
          a(j) = a(j + 1)
          a(j + 1) = tmp
        }
      }
    }
    println("-----排序后-----")
    a.foreach(print)




  }

}
