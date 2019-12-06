package com.imooc.scala.course04

object ApplyApp {
  def main(args: Array[String]): Unit = {
/*    for (i <- 1 to 10) {
      println("for body" + i)
      ApplyTest.incr
    }

    println(ApplyTest.count) // 10 说明object本身就是一个单例对象*/


    // 测试apply
    // 类名() ==> Object.apply
    // 对象() ==> Class.apply
    val b = ApplyTest() // ==> 调用了Object的apply方法。以后看到一个伴生类或伴生对象()，默认调用Object的apply
    println("~~~~~~~~~~~~~~~~~~~~")
    val c = new ApplyTest()
    println(c)
    c()// ==> 调用了Class的apply方法
  }
}


/**
 * 伴生类和伴生对象
 * 同名的，一个是class的一个是object的
 */
// 是下面的伴生类
class ApplyTest {

  println("Class ApplyTest enter...")

  def apply() = {
    println("Class ApplyTest apply...")

    // 在object中的apply中new class
    new ApplyTest
  }

  println("Class ApplyTest leave...")
}

// 是上面的伴生对象
object ApplyTest {
  println("Object ApplyTest enter...")

  var count = 0

  def incr = {
    count = count + 1
  }

  // 最佳实践：在Object的apply方法中new Class
  def apply() = {
    println("Object ApplyTest apply...")

    // 在object中的apply中new class
    new ApplyTest
  }

  println("Object ApplyTest leave...")
}