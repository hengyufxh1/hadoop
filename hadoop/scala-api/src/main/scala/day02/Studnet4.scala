package com.scala.day02

/**
 * apply方法
 */
class Studnet4(var stuName: String)

/**
 * 定义student4的apply方法
 */
object Student4 {
  def apply(name: String): Studnet4 = {
    println("调用了apply方法")
    new Studnet4(name)
  }
  // 测试程序
  def main(args: Array[String]): Unit = {
    var s1 = new Studnet4("Tom")
    println(s1.stuName)

    var s2 = Student4("Mary")
    println(s2.stuName)

  }
}


