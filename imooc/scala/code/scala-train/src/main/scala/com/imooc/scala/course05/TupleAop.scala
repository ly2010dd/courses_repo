package com.imooc.scala.course05

object TupleAop extends App {

  // 元祖：(...........)
  val a = (1,2,3,4,5)

  // 取值：有几个元素就_x
  println(a._1)
  println(a._2)

  // 遍历
  // a.productArity 长度
  // a.productElement(i) 取元素
  for (i <- 0 until(a.productArity)) {
    println(a.productElement(i))
}

val hostPort = ("localhost", 8080)
println(hostPort._1)
println(hostPort._2)
}
