package com.scala.day02

/**
 * 实现一个单利模式
 */
object CreditCard {

  // 定义一个变量保存信用卡卡号
  // 定义在object中，是静态的
  private[this] var creditCardNumber: Long = 0
  
  // 定义函数来产生新的卡号
  def generateCCNumber():Long={
    creditCardNumber+=1
    creditCardNumber
  }
  
  // 测试程序
  
  def main(args: Array[String]): Unit = {
//    产生新的卡号
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    
  }

}