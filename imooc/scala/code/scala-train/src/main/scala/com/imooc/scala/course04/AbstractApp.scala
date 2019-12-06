package com.imooc.scala.course04

object AbstractApp {
  def main(args: Array[String]): Unit = {
    val student = new Student2()
    println(student.name)
    student.speak
  }
}

/**
 * - 抽象类只有定义没有实现
 * - 抽象类不能被实例化
 */
abstract class Person2 {
  def speak

  val name:String
  val age:Int
}

class Student2 extends Person2 {
  // ???等价于空函数体{}
  //override def speak: Unit = ???
  override def speak: Unit = {
    println("speak")
  }

  override val name: String = "liy"
  override val age: Int = 18
}