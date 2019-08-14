package day04

/**
  * 需求：定义一个类，来操作整数
  */


class Vehicle1 {

  def drive() = println("Driving")
}

class Car1 extends Vehicle1{
  override def drive(): Unit = println("Car Driving")
}

class Bike1 extends Vehicle1{
  override def drive(): Unit = println("Bike Driving")
}



object ScalaUpperBound{
  // 定义驾驶交通工具的函数，规定他的上界是Vehicle
  def takeVehicle[T<:Vehicle1](v:T)=v.drive()

  def main(args: Array[String]): Unit = {
    var v:Vehicle1=new Vehicle1
    println(takeVehicle(v))

    var c:Car1 = new Car1
    println(takeVehicle(c))
  }
}


