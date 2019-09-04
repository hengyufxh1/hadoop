package day05.MWManager

/**
  * $功能描述 ：ActorMessage
  *
  * @author ：smart-dxw
  * @version ：2019/8/14 20:25 v1.0
  *          样本类 保存所有消息
  */
// 注册节点 worker ----> master
case class RegisterWorker(val id :String,val workerHost:String,val memory:String,val cores:String)
// 心跳信号  worker ----> master
case class HeartBeat(val workerid:String)

// 检查超时节点 master ----> master
case class CheckOfTimeOutWorker()

// ACK 信号 master ----> worker
case class RegisteredWorker(val workerHost:String)

// 提醒发送心跳信号 worker ----> worker
case class SendHeartBeat()
