package day04


/**
  * 样本类 使用 case class 实现模式匹配
  */

class Vehicle

case class Car(name: String) extends Vehicle

case class Bike(name: String) extends Vehicle

object Demo02 {

  def main(args: Array[String]): Unit = {

    var car: Vehicle = new Car("Car")
    car match {
      case Car(name) => println("汽车" + name)
      case Bike(name) => println("自行车" + name)
      case _ => println("其他")
    }
  }
}

