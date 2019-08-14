package com.scala.day02

class Student1 {
  private var stuID: Int = 0
  private var stuName: String = "Tom"
  private var age: Int = 20

  // 定义成员方法（函数） ：get set

  def getStuName(): String = stuName
  def setStuName(newName: String) = this.stuName = newName

  def getStuAge(): Int = age
  def setStuAge(newAge: Int) = this.age = newAge
}

/**
 * 测试student1类创建main函数 需要写到object中
 * 如果一样的话，那么这个object就叫做该class的伴生对象
 *
 */

object Student1 {
  def main(args: Array[String]): Unit = {
    // 创建一个学生对象
    var s1 = new Student1
    println(s1.getStuName() + "\t" + s1.getStuAge())

    s1.setStuName("Mary")
    s1.setStuAge(22)
    println(s1.getStuName() + "\t" + s1.getStuAge())

    /**
     * 直接访问私有属性
     * 在上面定义中，属性是private的
     * 如果属性是private，在类的外部，不能访问
     *
     */
    println("访问私有属性")
    println(s1.stuName + "\t" + s1.age)

    /**
     * 上面可以使用 s1.stuName来访问私有成员
     * 原因：
     *
     * 属性的set get方法：
     * 1.当属性是private时，scala会自动为其生成set get方法：
     * s1.stuID访问的stuID自动生成get方法 名字 stuID访问的stuID自动生成get方法
     *
     * 2.如果只希望scala生成get方法 不生成set方法，可以将其定义为常量因为常量的值不能改变
     *
     * 3.如果希望属性不能被外部访问，希望scala不要生成set get 方法，使用private[this]方法
     *
     */
  }
}