/**
  * 功能描述 ：Demo1
  *
  * @author ：smart-dxw
  * @version ：2019/8/5 22:25 v1.0
  */
object Demo1 {

  def main(args: Array[String]): Unit = {
    // for 循环
    // 定义一个集合
    var list = List("Mary", "Tom", "Mike")
    println("-----for循环的第一种写法-----")
    for (s <- list) println(s)

    // <- scala 中的提取符 把list中每个元素提取出来 赋给s


    println("-----for循环的第二种写法-----")
    // 打印出长度大于3的名字
    for {
      s <- list
      if (s.length > 3)
    } println(s)

    println("-----for循环的第三种写法-----")
    for (s <- list if s.length <= 3) println(s)

    println("-----for循环的第四种写法-----")
    // yield关键字，作用：产生一个新集合
    // 把list中 每个元素变成大写，返回一个新的集合

    var newList = for {
      s <- list
      s1 = s.toUpperCase()
    } yield (s1)
    for (s <- newList) println(s)


    println("--------------------------------------------------")
    println("-----while循环的写法-----")
    // 定义循环变量
    var i = 0
    while (i < list.length) {
      print(i + " --> " + list(i))
      i += 1
    }


    println("-----do while循环的写法-----")
    var j = 0
    do {
      println(list(j))
      j += 1
    } while (j < list.length)


    println("-----foreach循环的写法-----")
    list.foreach(println)








  }

}
