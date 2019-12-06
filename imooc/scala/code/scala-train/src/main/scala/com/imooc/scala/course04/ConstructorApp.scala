package com.imooc.scala.course04

object ConstructorApp {
  def main(args: Array[String]): Unit = {
    // 构造器
    val person = new Person("liy", 20)
    println(person.name + " : " + person.age + " : " + person.school)
    val person2 = new Person("tanli", 18, "F")
    println(person2.name + " : "
      + person2.age + " : "
      + person2.school + " : "
      + person2.gender)

    // 继承
    val student = new Student("liy", 18, "CS")
    println(student.name + " : " + student.major)

    // 重写
    println(student)
  }


  // 跟在class后面的叫主构造器
  class Person(val name:String, val age:Int) {
    println("Person Constructor enter...")

    val school = "ustc"
    var gender:String = _

    // 附属构造器
    def this(name:String, age:Int, gender:String) {
      this(name, age) //附属构造器的第一行代码必须调用主构造器或者其他附属构造器
      this.gender = gender
    }

    println("Person Constructor leave...")
  }

  // 继承与重写
  class Student(name:String, age:Int, var major:String) extends Person(name, age) {
    println("Person Student enter...")

    // 重写属性
    override val school = "peking"

    // 重写toString
    // override def toString: String = super.toString
    override def toString: String = "override toString: " + school


    println("Person Student leave...")

  }


}