package com.scala.day02

/**
 * 特质
 * 涉及到继承
 */

// 定义两个父类
// trait 也是抽象类
trait Human {
  // 抽象字段
  val id: Int
  val name: String

}

// 代表动作的trait
trait Action2 {
  //定义一个抽象函数
  def getActionName(): String

}

// 代表动作的trait
trait Action {
  //定义一个抽象函数
  def getActionName(): String

}

// 定义子类，希望从两个父类继承
class Student5(val id: Int, val name: String) extends Human with Action with Action2{
  def getActionName(): String = "Action is running"
}

object Demo4 {

  def main(args: Array[String]): Unit = {
    val s1 = new Student5(1, "duanxingwei")
    println(s1.id + "\t" + s1.name)
  }
}