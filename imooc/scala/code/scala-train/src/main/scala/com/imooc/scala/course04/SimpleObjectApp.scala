package com.imooc.scala.course04

object SimpleObjectApp {
  def main(args: Array[String]): Unit = {
    val person = new People()
    person.name = "liy"
    println(person.name + " .. " + person.age)
    println("invoke eat method: " + person.eat)
    person.watchFootball("China")

    person.printInfo()
  }
}

class People {
  // 自动为var变量生成get/set，自动为val只生成get
  //var name:String = ""
  //val age:Int = 10

  // 占位符的方式_，用占位符_必须要有类型，且必须是var
  var name:String = _
  val age = 10

  // private [this]表示只能在class内部使用
  private [this] val gender = "male"
  def printInfo(): Unit = {
    println("gender: " + gender)
  }

  def eat():String = {
    name + " eat... "
  }

  def watchFootball(teamName: String) = {
    println(name + " is watching match of " + teamName)
  }
}