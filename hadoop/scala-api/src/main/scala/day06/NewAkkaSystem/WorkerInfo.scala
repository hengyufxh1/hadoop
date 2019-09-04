package day06.NewAkkaSystem

/**
  * $功能描述 ：WorkInfo
  *
  * @author ：smart-dxw
  * @version ：2019/3/30 21:13 v1.0
  *          WorkerInfo类
  */
class WorkerInfo(val id: String, val workerHost: String, val memory: String, val cores: String) {

  // 保存心跳信息
  var lastHeartBeat: Long = System.currentTimeMillis()

  override def toString = s"WorkerInfo($id, $workerHost, $memory, $cores)"
}
