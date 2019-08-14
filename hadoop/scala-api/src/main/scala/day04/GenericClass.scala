package day04

/**
  * 需求：定义一个类，来操作整数
  */


object GenericClassInt {

  private var content:Int = 10

  def set(value:Int)={content=value}
  def get():Int={content}
}

/**
  * 需求：定义一个类来操作字符串
  */

object GenericClassString {

  private var content:String = ""

  def set(value:String)={content=value}
  def get():String={content}

}

class GenericClass[T]{
  private var content:T = _

  def set(value:T)={content=value}
  def get():T={content}

}



object GenericClass{
  def main(args: Array[String]): Unit = {
    var v1 = new GenericClass[Int]
    v1.set(1000)
    println("v1:"+v1.get())

    var v2 = new GenericClass[String]
    v2.set("Hello World")
    println("v2:"+v2.get())
  }
}




