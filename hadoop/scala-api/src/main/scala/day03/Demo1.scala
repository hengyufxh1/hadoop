package com.scala.day03

object Demo1 {


  def testFlatMap(): Unit = {
    val li = List(1, 2, 3)
    val result = li.flatMap(x => x match {
      case 3 => List('a', 'b')
      case _ => List(x * 2)
    })
    println(result)
  }

  def testMap(): Unit = {
    val li = List(1, 2, 3)
    val result = li.map(x => x match {
      case 3 => List('a', 'b')
      case _ => x * 2
    })
    println(result)
  }




  def main(args: Array[String]): Unit = {
    testFlatMap()
    println("**************")
    testMap()
  }
}
