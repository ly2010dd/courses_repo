package com.imooc.scala.course05

import scala.collection.mutable

object MapApp extends App{

  // 不可变Map
  val a = Map("liy" -> 18, "tanli" -> 17)
  // a("liy") = 20 不可变的Map不能赋值，报错：Error:(6, 3) value update is not a member of scala.collection.immutable.Map[String,Int]
  // a("lsi") = 40 报错
  println(a("liy"))
  println(a("tanli"))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 可变Map
  val b = scala.collection.mutable.Map("liy" -> 18, "tanli" -> 17)
  b("liy") = 20
  println(b("liy"))
  // println(b("pk")) 报错：没有key
  println(b.getOrElse("pk", 9)) // 优雅，常用
  b("lisi") = 40
  println(b.mkString(","))
  b += ("wangwu" -> 4, "zhangsan" -> 5)
  println(b.mkString(","))
  b -= "wangwu"
  println(b.mkString(","))

  val c = mutable.HashMap[String, Int]()
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 遍历
  for ((key, value) <- b) {
    println(key + " : " + value)
  }
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  for (key <- b.keySet) {
    println(key + " : " + b.getOrElse(key, 9))
  }
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  for (value <- b.values) {
    println(value)
  }
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  for ((key, _) <- b) {
    println(key + " : " + b.getOrElse(key, 9))
  }

}
