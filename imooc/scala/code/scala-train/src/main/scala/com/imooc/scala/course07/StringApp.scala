package com.imooc.scala.course07

object StringApp extends App {

  val s = "Hello"
  val name = "liy"

  //println(s + name) //low
  println(s"Hello: $name") // 插值

  val team = "AC Milan"
  println(s"Hello: $name, Welcome to $team")

  val b =
    """
      |这是一个多行字符串
      |hello
      |world
      |liy
      |""".stripMargin
  println(b)

  val c =
    s"""
      |这是一个多行字符串
      |hello
      |world
      |$name
      |""".stripMargin
  println(c)
}
