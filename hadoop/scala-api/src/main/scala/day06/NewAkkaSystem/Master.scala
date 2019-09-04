package day06.NewAkkaSystem

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * $功能描述 ：Master
  *
  * @author ：smart-dxw
  * @version ：2019/3/30 21:24 v1.0
  *          Master
  */
class Master extends Actor {

  // 保存WorkerId 和 Worker信息的map

  val idToWorker = new mutable.HashMap[String, WorkerInfo]()

  // 保存所有的Worker信息的Ser
  var workers = new mutable.HashSet[WorkerInfo]

  // Worker超时时间
  // 以毫秒为单位
  val WORKER_TIMEOUT = 10 * 1000

  override def preStart(): Unit = {
    context.system.scheduler.schedule(5 millis, WORKER_TIMEOUT millis, self, CheckOfTimeOutWorker)
  }

  override def receive: Receive = {
    case RegisterWorker(id, workerHost, memory, cores) => {
      if (!idToWorker.contains(id)) {
        val worker = new WorkerInfo(id, workerHost, memory, cores)
        workers.add(worker)
        idToWorker(id) = worker
        println("new register worker: " + worker)
        sender ! RegisteredWorker(worker.id)
      }
    }

    case HeartBeat(workerId) => {
      val workerInfo = idToWorker(workerId)
      println("get heartbeat message from : " + workerInfo)
      workerInfo.lastHeartBeat = System.currentTimeMillis()
    }

    case CheckOfTimeOutWorker => {
      // 检查超时的worker
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(w => currentTime - w.lastHeartBeat > WORKER_TIMEOUT).toArray
      for (worker <- toRemove) {
        workers -= worker
        idToWorker.remove(worker.id)
      }
      println("worker size:" + workers.size)
    }
  }
}

object Master extends App {
  val host = "localhost"
  val port = 8888
  // 创建actorSystem的必要参数
  val configStr =
    s"""
       |akka.actor.provider ="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname = "$host"
       |akka.remote.netty.tcp.port = "$port"
    """.stripMargin

  // 创建ActorSystem
  val conf = ConfigFactory.parseString(configStr)

  val actorSystem = ActorSystem.create("MasterActorSystem", conf)

  // 启动Actor Master 会被实例化，生命周期的方法会被调用
  actorSystem.actorOf(Props[Master], "Master")
}
