package day04

/**
  * 协变：泛型变量的值可以是本身类型或者其子类型
  * 用+表示
  * 用-表示 泛型变量值可以是本身或其父类型
  */

class Animal

class Bird extends Animal

class Sparrow extends Animal


// 定义吃东西的类
class EatSomething[+T](t: T)

class EatSomething2[-T](t: T)

object DemoClass1 {

  def main(args: Array[String]): Unit = {
    // 定义一个鸟吃东西的对象
    var c1: EatSomething[Bird] = new EatSomething[Bird](new Bird)

//    var c2: EatSomething[Animal] = new EatSomething[Animal](new Animal)

    // 问题：能否把c1赋给c2
    var c2: EatSomething[Animal] = c1

    /**
      * 报错原因：
      * 虽然 Bird是Animal的子类，但是EatSomething[Bird]并不是EatSomething[Animal]的
      */
    var c3 : EatSomething2[Bird] = new EatSomething2[Bird](new Bird)
//    var c4 : EatSomething2[Sparrow] = c3
  }
}

