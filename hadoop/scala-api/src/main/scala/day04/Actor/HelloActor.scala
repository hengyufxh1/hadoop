package day04.Actor

import akka.actor.{Actor, ActorSystem, Props}


/**
  * 功能描述 ：HelloActor
  *
  * @author ：smart-dxw
  * @version ：2019/8/13 22:38 v1.0
  */


class HelloActor extends Actor{
  def receive = {
    case "hello" => println("你好！")
    case _ => println("您是？")
  }
}






object HelloActor {

  def main(args: Array[String]): Unit = {
    // 新建一个ActorSystem
    val system = ActorSystem("hellosystem")

    // Actor构造函数
    val helloActor = system.actorOf(Props[HelloActor],name="helloactor")

    // 给actor发送消息

    helloActor ! "hello"
  }

}
