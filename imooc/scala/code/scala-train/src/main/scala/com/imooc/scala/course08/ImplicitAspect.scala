package com.imooc.scala.course08

import java.io.File

object ImplicitAspect extends App {

  implicit def man2superman(man:Man):Superman = new Superman(man.name)
  implicit def fileToRichFile(file: File): RichFile = new RichFile(file)
}
