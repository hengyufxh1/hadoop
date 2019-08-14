package com.scala.day02

/**
 * 类的继承
 */

/**
 * 父类Person人子类就是一个Emplyee
 */

class Person(val name: String, val age: Int) {
  def sayHello(): String = "Hello " + name + "and the age is " + age
}

/**
 * override 就是希望用子类的值覆盖父类中的值
 */
class Emplyee(override val name: String, override val age: Int, val salary: Int) extends Person(name, age) {

  /**
   * 重写父类的函数
   *
   */
  override def sayHello(): String = "子类的sayHello方法"

}

object Demo1 {

  def main(args: Array[String]): Unit = {
    var p1 = new Person("Tom", 20)
    println(p1.name + "\t"+p1.age)

    println(p1.sayHello())

    var p2 = new Emplyee("Mike", 25, 1000)
    println(p1.sayHello())

    
    // 通过匿名子类来实现继承
    var p3 : Person = new Person("Mary",25){
      override def sayHello():String = "匿名子类中的sayHello"
    }
    println(p3.sayHello())
    
    
    
    
  }
}