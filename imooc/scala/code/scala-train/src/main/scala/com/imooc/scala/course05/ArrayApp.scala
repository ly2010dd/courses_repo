package com.imooc.scala.course05

object ArrayApp extends App {
  // 定长数组
  // 方式1
  val a = new Array[String](5)
  println(a.length)
  a(1) = "hello"
  println(a(1))

  // 方式2
  val b = Array("hadoop", "spark", "storm")
  b(1) = "flink" // b 是val，引用不能改变，但是可以改其中的值
  println(b(1))

  val c = Array(1,2,3,4,5,6,7,8,9)
  println(c.sum)
  println(c.max)
  println(c.min)
  println(c.mkString)
  println(c.mkString(","))
  println(c.mkString(" "))
  println(c.mkString("<", "-", ">"))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 可变数组
  val d = scala.collection.mutable.ArrayBuffer[Int]()
  println(d.length)
  d += 1
  d += 2
  d += (3,4,5)
  d ++= Array(6,7,8)
  println(d)
  d.insert(0, 0)
  println(d)
  d.remove(1)
  println(d)
  d.remove(0,3)
  println(d)
  d.trimEnd(2)
  println(d)

  // 可变转不可变
  println(d.toArray.mkString(","))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 遍历
  for(i <- 0 until d.length) {
    println(d(i))
  }
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  for(ele <- d) {
    println(ele)
  }
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 逆序
  for (i <- (0 until d.length).reverse) {
    println(d(i))
  }
}
