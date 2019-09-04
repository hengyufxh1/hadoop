package day06.NewAkkaSystem

/**
  * $功能描述 ：ActorMessage
  *
  * @author ：smart-dxw
  * @version ：2019/3/30 21:16 v1.0
  *          样本类：保存所有信息
  */

// worker ----> master 注册节点的信息


case class RegisterWorker(val id: String, val workerHost: String, val memory: String, val corse: String)

// worker ----> master 发送心跳信号

case class HeartBeat(val workid: String)

// master ----> worker 注册完成 ACK
case class RegisteredWorker(valWorkerHost: String)

// master ----> master 检查超时节点

case class CheckOfTimeOutWorker()

// worker ----> worker 触发发送心跳

case class SendHeartBeat()
