package day05.MWManager

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * $功能描述 ：Worker
  *
  * @author ：smart-dxw
  * @version ：2019/8/14 20:32 v1.0
  *          从节点
  */
class Worker extends Actor {

  // Woeker 会给 Master 发送消息，所以 Worker需要有Master的引用
  var master: ActorSelection = null

  // 生成UUID 作为 Worker 标识
  val id = UUID.randomUUID().toString


  override def receive: Receive = {

    case RegisteredWorker(masterUrl) => {
      context.system.scheduler.schedule(0 millis, 5000 millis, self, SendHeartBeat)
    }
    case SendHeartBeat => {
      println(" worker send heartbeat")
      master ! HeartBeat(id)
    }
  }

  // 构造方法执行完后，执行如下方法
  override def preStart(): Unit = {
    // Worker 给 Master 发送消息
    master = context.system.actorSelection("akka.tcp//MasterActorSystem@localhost:8888/user/Master")
    // Worker 向 Master 发送注册信息
    master ! RegisterWorker(id, "localhsot", "10240", "8")
  }
}

object Worker extends App{
  val clientPort = 8892

  //创建WorkerActorSystem的必要参数
  val configStr =
    s"""
       |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.port = $clientPort
       """.stripMargin

  val config = ConfigFactory.parseString(configStr)

  val actorSystem = ActorSystem("WorkerActorSystem", config)

  //启动Actor，Master会被实例化，生命周期方法会被调用
  actorSystem.actorOf(Props[Worker], "Worker")

}
