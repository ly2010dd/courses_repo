package com.imooc.scala.course08

import java.io.File
import ImplicitAspect._

object ImplicitApp extends App {

  // 定义隐式转换函数即可
  // 已封装ImplicitApp中
  // implicit def man2superman(man:Man):Superman = new Superman(man.name)
  val man = new Man("Liy")
  man.fly() // 本来Man中是没有fly方法的

  // 已封装ImplicitApp中
  // implicit def fileToRichFile(file: File): RichFile = new RichFile(file)
  val file = new File("/Users/milesyli/hello.txt")
  val txt = file.read() // 本来File中是没有read方法的
  println(txt)

  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
}


class Man(val name:String) {
  def eat(): Unit = {
    println(s"man [ $name ] eat ......")
  }
}

class Superman(val name:String) {
  def fly(): Unit = {
    println(s"superman [ $name ] fly ......")
  }
}

class RichFile(val file:File) {
  def read() = {
    scala.io.Source.fromFile(file.getPath).mkString
  }
}