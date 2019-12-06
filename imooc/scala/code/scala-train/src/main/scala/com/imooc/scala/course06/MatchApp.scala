package com.imooc.scala.course06

import scala.util.Random

object MatchApp extends App {
  // 基础的模式匹配
  val names = Array("liy", "tan li", "liu yu")
  val name = names(Random.nextInt(names.length))

  name match {
    case "liy" => println("李老师...")
    case "tan li" => println("谈老师...")
    case _ => println("不知道...")
  }

  def judgeGrade(grade:String) = {
    grade match {
      case "A" => println("Excellent...")
      case "B" => println("Good...")
      case "C" => println("Just so so...")
      case _ => println("You need work harder...")
    }
  }
  judgeGrade("A")
  judgeGrade("C")
  judgeGrade("D")
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 加条件进行匹配
  def judgeGrade(name:String, grade:String) = {
    grade match {
      case "A" => println("Excellent...")
      case "B" => println("Good...")
      case "C" => println("Just so so...")
      case _ if(name == "lisi") => println(name + ", you are a good boy, but...")
      case _ => println("You need work harder...")
    }
  }
  judgeGrade("zhangsan", "D")
  judgeGrade("lisi", "A")
  judgeGrade("lisi", "D") // 双重过滤
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // Array模式匹配
  def greeting(array:Array[String]): Unit = {
    array match {
      case Array("zhangsan") => println("Hi, zhangsan")
      case Array(x, y) => println("Hi: " + x + " , " + y)
      case Array("zhangsan", _*) => println("Hi, zhangsan and other friends...")
      case _ => println("Hi, everybody...")
    }
  }
  greeting(Array("zhangsan"))
  greeting(Array("lisi", "wangwu"))
  greeting(Array("zhangsan", "lisi", "wangwu"))
  greeting(Array("lisi", "wangwu", "zhangsan"))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // List模式匹配
  def greeting(list:List[String]) = {
    list match {
      case "zhangsan"::Nil => println("Hi, zhangsan")
      case x::y::Nil => println("Hi, " + x + " , " + y)
      case "zhangsan"::tail => println("Hi, zhangsan and other friends...")
      case _ => println("Hi, everybody...")
    }
  }
  greeting(List("zhangsan"))
  greeting(List("lisi", "zhangsan"))
  greeting(List("zhangsan", "lisi"))
  greeting(List("zhangsan", "lisi", "wangwu"))
  greeting(List("wangwu", "zhangsan", "lisi"))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 类型匹配
  def matchType(obj:Any) = {
    obj match {
      case x:Int => println("Int")
      case s:String => println("String")
      case m:Map[_,_] => m.foreach(println)
      case _ => println("other type")
    }
  }
  matchType(1)
  matchType("1")
  matchType(1f)
  matchType(Map("name" -> "pk"))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // case class模式匹配
  // 需要传入的是顶层的父类
  def caseClassMatch(person: Person) = {
    person match {
      case CTO(name, floor) => println("CTO name is: " + name + " , floor is:" + floor)
      case Employee(name, floor) => println("Employee name is: " + name + " , floor is:" + floor)
      case _ => println("other")
    }
  }
  class Person
  case class CTO(name:String, floor:String) extends Person
  case class Employee(name:String, floor:String) extends Person
  case class Other(name:String) extends Person
  caseClassMatch(CTO("Liy", "22"))
  caseClassMatch(Employee("zhangsan", "2"))
  caseClassMatch(Other("lisi"))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // Some None模式匹配
  val grades = Map("Liy" -> "A", "zhangsan" -> "B")
  def getGrade(name:String) = {
    val grade = grades.get(name)
    grade match {
      case Some(grade) => println("your grade is : " + grade)
      case None => println("Sorry...")
    }
  }
  getGrade("zhangsan")
  getGrade("Liy")
  getGrade("lisi")

}
