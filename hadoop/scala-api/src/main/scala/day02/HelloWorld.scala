package com.scala.day02

object HelloWorld extends App{
  println("Hello World")
  
  if(args.length>0){
    println("有参数")
    
  }else{
    println("没有参数")
  }
}