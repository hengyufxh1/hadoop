package com.scala.day02

/**
 * 类的柱构造和辅助构造器
 */

class Student3(var stuName: String, var age: Int) {
  // 属性
  private var gender: Int = 1

  // 生成辅助构造器，辅助构造器可以有多个
  // 辅助构造器就是一个函数，只不过这个函数的名字叫this
  def this(age: Int) {
    this("Mike", age) // new Student3("Mike",age)
    println("这是辅助构造器")
  }

  def this() {
    this(10)
    println("这是辅助构造器2")
  }
}

object Student3 {

  def main(agrs: Array[String]): Unit = {
    // 使用主构造器创建学生对象
    var s1 = new Student3("Tom", 3)
    println(s1.stuName + "\t" + s1.age)

    // 使用辅助构造器
    var s2 = new Student3
    s2.gender = 0
    s2.stuName = "s2"
    println(s2.stuName + "\t" + s2.age + "\t" + s2.gender)
  }
}