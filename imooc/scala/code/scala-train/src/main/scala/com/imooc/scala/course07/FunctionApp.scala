package com.imooc.scala.course07

object FunctionApp extends App {

  // 命名的函数
  def sayHello(name: String) = {
    println("Hi, " + name)
  }

  sayHello("Liy")

  // 匿名函数
  // 语法：(参数名: 参数类型...) => 函数体
  // eg: (x:Int) => x+1
  val m1 = (x: Int) => x + 1
  println(m1(10))

  def add = (x: Int, y: Int) => {
    x + y
  } //等价于def add(x:Int, y:Int)={x+y}
  println(add(2, 3))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // currying函数
  def sum(a: Int, b: Int) = a + b

  println(sum(2, 3))

  //===> currying化后，将入参转化为2个
  def sum2(a: Int)(b: Int) = a + b

  println(sum2(2)(3))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 高阶函数
  // *****重中之重*****
  val l = List(1, 2, 3, 4, 5, 6, 7, 8)

  // map: 逐个去操作集合中的每个元素
  val a = l.map((x: Int) => x + 1)
  println(l.mkString(","))
  println(a.mkString(","))

  val b = l.map((x) => x + 1) // 简写
  println(b.mkString(","))

  val c = l.map(x => x * 2) //只有一个元素时x的括号也可以省略，但两个以上元素就不可以了
  println(c.mkString(","))

  val d = l.map(_ * 3) //用占位符的方式
  println(d.mkString(","))

  l.map(_ * 2).foreach(println)
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // filter
  l.map(_ * 2).filter(_ > 8).foreach(println)
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // take 取集合的前几个
  l.take(2).foreach(println)
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // reduce 集合中相邻两元素两两操作
  println(l.reduce(_+_))
  println(l.reduce(_-_))

  println(l.reduceLeft(_-_)) // (((1-2)-3)-4)
  println(l.reduceRight(_-_))// (1-(2-(3-4)))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // fold 输入数据转化成另外一种格式返回
  println(List.range(1, 5).fold(100)(_-_)) // 等价于foldleft
  println(List.range(1, 5).foldLeft(100)(_-_))  // 100-1-2-3-4 = 90
  println(List.range(1, 5).foldRight(100)(_-_))  // 1-(2-(3-(4-100))) = 98
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // max min sum
  println(l.max)
  println(l.min)
  println(l.sum)
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // flatten 压扁
  val f = List(List(1,2), List(3,4), List(5,6))
  println(f.mkString(","))
  println(f.flatten.mkString(","))

  val f1 = f.map(_.map(_ * 2))
  val f2 = f.flatMap(_.map(_ * 2))
  println(f1.mkString(","))
  println(f2.mkString(","))
}
