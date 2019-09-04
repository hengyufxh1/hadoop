package day05.MWManager

/**
  * $功能描述 ：WorkerInfo
  *
  * @author ：smart-dxw
  * @version ：2019/8/14 20:21 v1.0
  *          保存Worker基本信息
  */
class WorkerInfo(val id: String, val workerHost: String, val memory: String, val cores: String) {

  // 保存心跳信息
  var lastHeartBeat:Long = System.currentTimeMillis()

  override def toString = s"WorkerInfo($id,$workerHost,$memory,$cores)"

}
