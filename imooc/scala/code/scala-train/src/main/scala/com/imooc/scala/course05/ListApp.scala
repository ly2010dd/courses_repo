package com.imooc.scala.course05

object ListApp extends App {

  // 定长List
  val l = List(1,2,3,4,5)
  println(l.head)
  println(l.tail)

  val l2 = 1 :: Nil // 把1和空集合拼接成一个新的集合, head::tail
  println(l2.mkString(" "))

  val l3 = 2 :: l2
  println(l3.mkString(" "))

  val l4 = 1 :: 2 :: 3 :: Nil
  println(l4.mkString(" "))
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 变长List
  val l5 = scala.collection.mutable.ListBuffer[Int]()
  l5 += 2
  l5 += (3,4,5)
  l5 ++= List(6,7,8,9)
  println(l5.mkString(" "))

  l5 -= 2
  l5 -= 3
  l5 -= (1,4)
  l5 --= List(5,6,7,8)
  println(l5.mkString(" "))

  //转定长
  l5 += (1,2,3,4)
  l5.toList
  l5.toArray

  println(l5.tail.head)
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")

  // Set 不可重复的
  val set = Set(1,2,2,1,4,3)
  println(set.mkString(" "))

  val set1 = scala.collection.mutable.Set[Int]()
  set1 += 1
  set1 += (1,1,2,2)
  println(set1.mkString(" "))
}
