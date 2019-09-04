package day05.MWManager

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable

import scala.concurrent.duration._


import scala.concurrent.ExecutionContext.Implicits.global


/**
  * $功能描述 ：Master
  *
  * @author ：smart-dxw
  * @version ：2019/8/14 20:35 v1.0
  *          主节点
  */
class Master extends Actor {

  // 保存WorkerId和Worker信息的Map
  val idToWorker = new mutable.HashMap[String, WorkerInfo]()

  // 保存所有Worker信息的Set
  val workers = new mutable.HashSet[WorkerInfo]()

  // Worker超时事件
  val WORKER_TIMEOUT = 10 * 1000


  override def receive: Receive = {
    case RegisterWorker(id, workerHost, memory, cores) => {
      if (idToWorker.contains(id)) {
        val worker = new WorkerInfo(id, workerHost, memory, cores)
        workers.add(worker)
        idToWorker(id) = worker
        println("new register worker:" + worker)
        sender ! RegisteredWorker(worker.workerHost)
      }
    }
    case HeartBeat(workerId) => {
      val workerInfo = idToWorker(workerId)
      println("get heratbeat message from:" + workerInfo)
      workerInfo.lastHeartBeat = System.currentTimeMillis()

    }

    case CheckOfTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(w => currentTime - w.lastHeartBeat > WORKER_TIMEOUT).toArray
      for(worker<-toRemove){
        workers -= worker
        idToWorker.remove(worker.id)
      }
      println("worker size:" + workers.size)
    }
  }

  override def preStart(): Unit = {
    context.system.scheduler.schedule(5 millis, WORKER_TIMEOUT millis, self, CheckOfTimeOutWorker)
  }
}


object Master extends App{
  val host = "localhost"
  val prot = 8888

  val configStr =
    s"""
       |akka.actor.provider ="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname = "$host"
       |akka.remote.netty.tcp.port = "$prot"
     """.stripMargin

  val config = ConfigFactory.parseString(configStr)
  val actorSystem = ActorSystem.create("MasterActorSystem",config)

  // 启动actor
  actorSystem.actorOf(Props[Worker], "Worker")


}