package com.imooc.scala.course07

import scala.util.Random

/**
 * 偏函数：被包在花括号内没有match的一组case语句
 */
object PartialFunctionApp {
  val names = Array("liy", "tan li", "liu yu")
  val name = names(Random.nextInt(names.length))

  name match {
    case "liy" => println("李老师...")
    case "tan li" => println("谈老师...")
    case _ => println("不知道...")
  }

  // def 函数名:PartialFunction[入参类型, 出参类型]
  def sayChinses:PartialFunction[String, String] = {
    case "liy" => "李老师..."
    case "tan li" => "谈老师..."
    case _ => "不知道..."
  }

  println(sayChinses("liy"))
}
