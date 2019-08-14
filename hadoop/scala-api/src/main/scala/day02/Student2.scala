package com.scala.day02

import scala.collection.mutable.ArrayBuffer

/**
 * 需求：定一个学生类，同时要保存学生的成绩信息
 */

class Student2 {
  private var stuName: String = "Tom"
  private var stuAge: Int = 20

  // 定义一个数组来保存课程成绩

  private var courseList = new ArrayBuffer[Course]()

  //定义一个函数，用于添加学生的课程成绩
  def addNewCourse(cname: String, grade: Int): Unit = {
    var course = new Course(cname, grade)

    // 添加到学生对象中
    courseList += course
  }

  // 定义课程类：主构造写在类的后面
  class Course(var courseName: String, var grade: Int) {
  }

}

object Student2 {
  def main(args: Array[String]): Unit = {

    var s = new Student2

    // 给学生添加课程信息
    s.addNewCourse("Chinese", 80)
    s.addNewCourse("Math", 70)
    s.addNewCourse("English", 50)

    //输出
    println(s.stuName + "\t" + s.stuAge)
    println("课程信息")

    for (c <- s.courseList) println(c.courseName + "\t" + c.grade)
  }
}