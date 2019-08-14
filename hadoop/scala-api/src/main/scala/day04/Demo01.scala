package com.scala.day04

object Demo01 {

  def main(args: Array[String]): Unit = {

    /**
      *模式匹配
      */

    var ch1 = '+'
    var sign = 0
    ch1 match {
      case '+' => sign = 1
      case '-' => sign = -1
      case _ => sign = 0
    }
    println(sign)

    //2、scala的守卫：匹配某种类型的所有制case _ if
    // 匹配所有的数字，如果ch2是一个数字，则把标志位赋值为ch2
    var ch2 = '+'
    var digit = -1
    ch2 match {
      case '+' => println("这是一个加号")
      case '-' => println("这是一个减号")
      case _ if Character.isDigit(ch2) => digit = Character.digit(ch2, 10) // 10表示是10进制
      case _ => println("其他")
    }

    /**
      * 在模式匹配中使用变量
      */

    var mystr = "Hello World"
    // 取出某个字符，付给模式匹配中的变量
    mystr(7) match {
      case '+' => println("这是一个加号")
      case '-' => println("这是一个减号")
      case abc123 => println(abc123)
    }


    /**
      * 4、类型匹配相当于Java中的instanceof
      */

    var v4: Any = 100
    v4 match {
      case x: Int => println("这是一个整数")
      case x: String => println("这是一个字符串")
      case _ => println("其他类型")
    }

    /**
      * 5、匹配数组和类别
      */
    var myArray = Array(1, 2, 3)
    myArray match {
      case Array(0) => println("数组中只有一个0")
      case Array(x, y) => println("数组中包含两个元素")
      case Array(x, y, z) => println("数组中包含三个元素")
      case Array(x, _*) => println("数组中包含多个元素")
    }

    var myList = List(1, 2, 3)
    myList match {
      case List(0) => println("列表中只有一个0")
      case List(x, y) => println("列表中包含两个元素，和是：" + (x + y))
      case List(x, y, z) => println("列表中包含三个元素，和是：" + (x + y + z))
      case List(x, _*) => println("列表中包含多个元素" + (myList.sum))
    }


  }
}

