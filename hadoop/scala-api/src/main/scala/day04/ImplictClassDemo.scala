package day04

/**
  * 功能描述 ：ImplictClassDemo
  *
  * @author ：smart-dxw
  * @version ：2019/8/13 22:20 v1.0
  *          隐式类
  */
object ImplictClassDemo {
  def main(args: Array[String]): Unit = {
    // 执行两个数字求和
    println("两个数字的和是："+1.add(2))


  }

  /**
    * 执行过程：
    * 首先把1转换成恒Cala(1)类
    * 在调用add方法
    * @param x
    */
  implicit class Calc(x:Int){
    def add(y:Int):Int = x+y
  }
}
