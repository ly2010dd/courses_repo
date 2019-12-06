package com.imooc.scala.course04

object CaseClassApp {
  def main(args: Array[String]): Unit = {
    println(Dog("wangcai").name)
  }
}

// case class 不用new
// 通常用在模式匹配中
case class Dog(name:String)