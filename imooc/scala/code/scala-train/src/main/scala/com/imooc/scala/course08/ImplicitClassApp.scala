package com.imooc.scala.course08

object ImplicitClassApp extends App {

  // 表示所有Int类型都有了add方法
  implicit class Calculator(x:Int) {
    def add(a:Int) = a + x
  }
  println(1.add(3)) // ok 4
}

