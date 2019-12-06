package com.imooc.scala.course08

object ImplicitParamApp extends App {

  /* 4
  implicit val test = "test"*/

  // 隐式参数
  def testParam(implicit  name:String): Unit = {
    println(name + "...")
  }

  /* 4
  testParam ok test...*/

  /* 1
  testParam 报错：could not find implicit value for parameter name: String testParam
  testParam("liy") ok liy...*/

  /* 2
  implicit val name = "implicit_name"
  testParam //ok implicit_name...*/

  /* 3
  implicit val s1 = "s1"
  implicit val s2 = "s2"
  testParam //error Error:(17, 3) ambiguous implicit values: both value s1 in object ImplicitParam of type => String and value s2 in object ImplicitParam of type => String match expected type String
  */

}
