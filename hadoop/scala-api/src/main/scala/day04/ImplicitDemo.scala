package day04

/**
  * 功能描述 ：ImplicitDemo
  *
  * @author ：smart-dxw
  * @version ：2019/8/13 22:00 v1.0
  *
  *          定义隐式转换函数
  */


class Fruit(name: String) {
  def getFruitName(): String = name
}

class Mokkey(f: Fruit) {
  def say() = println("monkey like " + f.getFruitName())
}

object ImplicitDemo {

  def main(args: Array[String]): Unit = {
    // 定义一个水果对象
    var f: Fruit = new Fruit("Banana")
    f.say()
  }


  /**
    * 注意：近身使用隐式转换，隐式转换会导致scala可读性进一步变差
    * 隐式转换函数：会自动调用
    * @param f
    * @return
    */
  implicit def fruit2Monkey(f:Fruit):Mokkey={
    new Mokkey(f)
  }
}
