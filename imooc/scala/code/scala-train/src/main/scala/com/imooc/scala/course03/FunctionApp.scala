package com.imooc.scala.course03

object FunctionApp {
  def main(args: Array[String]): Unit = {
//    println(add(2, 3))
//    println(three())
//    println(three) //Scala中没有入参的函数，调用时括号是可以省略的
//    sayHello()
//    sayHello
//    sayHello("liy")

/*    // 默认参数
    sayName() //这种情况括号不能省略
    sayName("lisi")

    // 明明参数
    println(speed(100, 10))
    println(speed(distance = 100, time = 10))
    println(speed(time = 10, distance = 100))

    // 可变参数
    println(sum(1, 2))
    println(sum(1, 2, 3))
    println(sum(1, 2, 3, 4))*/

    for (i <- 1 to 10 if i % 2 == 0) {
      println(i)
    }

    val courses = Array("Hadoop", "Spark", "Zookeeper", "Kafka")
    for (course <- courses) { //遍历集合
      println(course)
    }

    val courses2 = Array("Hadoop", "Spark", "Zookeeper", "Kafka")
    courses2.foreach(course => println(course)) //遍历集合，course是集合中的每个元素，=>就是将左边的course作用上一个函数

    var (num, sum) = (100, 0)
    while(num > 0) {
      sum = sum + num
      num = num - 1
    }
    println(sum)
  }

  def add(x: Int, y: Int): Int = {
    x + y // 最后一行就是返回值，不需要return
  }

  def three() = 1 + 2

  def sayHello(): Unit = {
    println("Say Hello...")
  }

  def sayHello(name: String): Unit = {
    println("Say Hello: " + name)
  }

  // 默认参数
  def sayName(name: String = "liy"): Unit = {
    println("Say Hello: " + name)
  }

  // 命名参数
  def speed(distance:Float, time: Float): Float = {
    distance / time
  }

  // 可变参数
  def sum(numbers:Int*): Int = {
    var result = 0
    for (number <- numbers) {
      result += number
    }
    result
  }
}