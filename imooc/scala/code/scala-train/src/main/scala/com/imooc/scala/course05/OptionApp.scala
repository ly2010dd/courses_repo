package com.imooc.scala.course05

/**
 * case object None extends Option[Nothing] {
 *   def isEmpty = true
 *   def get = throw new NoSuchElementException("None.get")
 * }
 *
 * final case class Some[+A](x: A) extends Option[A] {
 *    def isEmpty = false
 *    def get = x
 * }
 */
object OptionApp extends App {

  val m = Map(1 -> 2)
  // println(m(1))
  // println(m(2)) // 报错

  println(m.get(1)) // 输出：Some(2)
  println(m.get(1).get) // 输出：2

  println(m.getOrElse(2, "None"))
}
