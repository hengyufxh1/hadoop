package com.scala.day02

/**
 * 抽象类、抽象字段
 * 抽象类：可以包含抽象方法，但只能用于继承，与java一样
 * 父类：抽象类----交通工具
 * 
 */

abstract class Vehicle{
  // 定义抽象方法：没有实现的方法
  def checkType():String
  
}

// 子类：自行车、汽车 从父类继承，如果子类没有实现父类的抽象方法，那么子类也必须是抽象的
class Car extends Vehicle {
  def checkType(): String = " I am a Car "
}

class Bike extends Vehicle{
  override def checkType():String=" I am a Bike"
}

object Demo2 {
  def main(args: Array[String]): Unit = {
    var v1 : Vehicle = new Car
    println(v1.checkType())
    
    var v2 : Vehicle = new Bike
    println(v2.checkType())
    
    
  }
  
}