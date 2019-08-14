package day04.Actor

import akka.actor._

/**
  * $功能描述 ：TowActorDemo
  *
  * @author ：smart-dxw
  * @version ：2019/8/13 22:41 v1.0
  *          两个Actor相互发送消息
  */


case object AMessage
case object BMessage
case object StartMessage
case object StopMessage



class A extends Actor{

  def receive  = {
    case BMessage =>
      println("receive B mesage")
      sender ! AMessage
    case StopMessage =>
      println("stop!!")
      context.stop(self)
//      context.system.finalize()
  }
}

class B(a:ActorRef) extends Actor{
  var count = 0
  def incrementAndPrint: Unit ={
    count +=1
    println("count + 1")
  }
  def receive = {
    case AMessage=>
      if(count > 9){
        sender ! StopMessage
        println("stop!!")
        context.stop(self)
//        context.system.finalize()
      }else{
        println("Receive AMessage")
        incrementAndPrint
        sender ! BMessage
      }
    case StartMessage=>
      println("Receive StartMessage")
      //发送一个消息
      a ! BMessage
  }
}


object TowActorDemo {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("ABsystem")
    val a = system.actorOf(Props[A],name="A")
    val b = system.actorOf(Props(new B(a)),name="B")

    b ! StartMessage

  }

}
