package com.scala.day02

/**
 * 抽象字段
 * 抽象字段=抽象属性
 * 定义：没有初始值的字段
 */
abstract class Person1 {
  // 定义抽象字段，抽象字段只有get方法
  val id: Int
  val name: String
}

class Emplyee1(val id: Int, val name: String) extends Person1 {

}

class Emplyee2() extends Person1 {
  val id: Int = 2
  val name: String = ""
}

object Demo3 {

}