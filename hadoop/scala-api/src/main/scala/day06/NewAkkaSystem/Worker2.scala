package day06.NewAkkaSystem

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * $功能描述 ：Worker2
  *
  * @author ：smart-dxw
  * @version ：2019/3/30 21:21 v1.0
  */
class Worker2 extends Actor {
  // Worker 端持有Master端的引用
  var master: ActorSelection = null

  // 生成一个UUID 作为Worker标识

  val id = UUID.randomUUID().toString

  val timeOutWorker = 10*1000

  // 启动 给master发送注册信号
  override def preStart(): Unit = {
    // 先在系统中建立连接请求
    master = context.system.actorSelection("akka.tcp://MasterActorSystem@localhost:8888/user/Master")

    // 初始化向Master发送注册信息
    master ! RegisterWorker(id, "localhost", "10240", "8")

  }

  override def receive: Receive = {
    case RegisteredWorker(masterURL) => {
      context.system.scheduler.schedule(0 millis, timeOutWorker millis, self, SendHeartBeat)
    }
    case SendHeartBeat => {
      println("worker send heartbeat")
      master ! HeartBeat(id)
    }
  }
}

object Worker2 extends App {
  val clientPort = 8893
  val configStr =
    s"""
       |akka.actor.provider ="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.port = $clientPort
    """.stripMargin

  // 创建ActorSystem
  val config = ConfigFactory.parseString(configStr)

  val actorSystem = ActorSystem("WorkerActorSystem", config)

  // 启动Actor Master 会被实例化，生命周期的方法会被调用
  actorSystem.actorOf(Props[Worker], "Worker")

}


