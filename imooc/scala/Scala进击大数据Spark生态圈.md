# 初识Scala

### 1-1 Scala导学
- 环境参数
    - JDK1.8
    - Scala2.11.8
    - IDEA
    - Spring Boot

### 1-4 Scala意义
- Spark、Kafka、Flink生态系统源码
    - 优雅
    - 开发速度
    - 融合到生态圈
    
### 1-5 Scala安装
    - JDK1.8
    - download scala tgz and unzip
    - ~/.bash_profile
        ```
        export SCALA_HOME=/home/hadoop/app/scala-2.11.8
        export PATH=$SCALA_HOME/bin:$PATH
        ```
    - source ~/.bash_profile
    - ./scala
    
### 1-7 Helloworld案例Java和Scala对比
- HelloWorld.scala
    ```
    bject HelloWorld {
      def main(args: Array[String]) {
        println("Hello, world!")
      }
    }
    ```
- 编译执行
    ```
    scalac HelloWorld.scala
    scala HelloWorld
    ```

### 2-1 Scala入门
- val vs var
- 基本数据类型
- lazy在Scala中的应用
- Scala开发工具
- 使用IDEA整合Maven构建Scala

### 2-2 val vs var
- val: 值
    - final
    - val 值名称(:类型) = xxx
        - val money:Int = 200
        - val moeny = 200
    - 不能被重新赋值
- var: 变量
    - var 值名称(:类型) = xxx
    - 可以被重新赋值
- val更常用

### 2-3 Scala数据类型
- Scala基本数据类型
    - Byte/Char
    - Short/Int/Long/Float/Double
    - Boolean
- 类型转换
    ```
    # 类型转换
    val g = 10.asInstanceOf[Double]
    # 类型判断
    val h = 10.isInstanceOf[Int]
    ```

### 2-4 lazy在Scala中的使用
- lazy
    ```
    scala> lazy val a = 1
    a: Int = <lazy>
    scala> a
    res0: Int = 1
    ```
- 定义时并不立马加载，直到执行时才真正加载
- 就算执行有异常时，定义为lazy后也可以编译通过

### 2-6 IDEA整合Maven运行Scala
- 先安装scala，配置环境变量
- 在IDEA中安装scala插件
    - preference->plugins->scala
    - 在IDEA中配置下scala sdk路径
- 新建Maven项目
    - 勾选create from archetype
    - 选scala-archetype-simple
- 新建一个scala class，类型为Object
    ```
    package com.imooc.scala
    
    object HelloWorld {
      def main(args: Array[String]): Unit = {
        println("Hello World... FROM IDEA...")
      }
    }
    ```
    
# 第三章 Scala函数

### 3-1 课程目录
- 方法的定义和使用
- 默认参数
- 命名参数
- 可变参数
- 条件表达式
- 循环表达式

3-2 函数定义和使用
- 函数/方法的定义
    ```
    def 方法名(参数名1: 参数类型, 参数名2: 参数类型): 方法返回值类型 = {
        // 方法体
        
        // 方法体内的最后一行为返回值，不需要使用return
    }
    
    # eg
    def max(x: Int, y: Int): Int = {
      if (x > y)
        x
      else
        y
    }
    ```
- 实践
    ```
    object FunctionApp {
      def main(args: Array[String]): Unit = {
        println(add(2, 3))
        println(three())
        println(three) //Scala中没有入参的函数，调用时括号是可以省略的
        sayHello()
        sayHello
        sayHello("liy")
      }
    
      def add(x: Int, y: Int): Int = {
        x + y // 最后一行就是返回值，不需要return
      }
    
      def three() = 1 + 2
    
      def sayHello(): Unit = {
        println("Say Hello...")
      }
    
      def sayHello(name: String): Unit = {
        println("Say Hello: " + name)
      }
    }
    
    # 输出
    5
    3
    3
    Say Hello...
    Say Hello...
    Say Hello: liy
    ```

### 3-3 3-4 3-5 默认参数与命名参数 可变参数
- 实践
    ```
    // 默认参数
    sayName() //这种情况括号不能省略
    sayName("lisi")
    
    def sayName(name: String = "liy"): Unit = {
      println("Say Hello: " + name)
    }

    // 命名参数
    println(speed(100, 10))
    println(speed(distance = 100, time = 10))
    println(speed(time = 10, distance = 100)) // 少见，不建议
    
    def speed(distance:Float, time: Float): Float = {
      distance / time
    }
    
    // 可变参数
    println(sum(1, 2))
    println(sum(1, 2, 3))
    println(sum(1, 2, 3, 4))
    
    def sum(numbers:Int*): Int = {
        var result = 0
        for (number <- numbers) {
          result += number
        }
        result
    }
    ```
    
### 3-7 循环表达式
- to
    - 左闭右闭
        ```
        scala> 1 to 10
        res1: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        
        1 to 10 等价于
        1.to(10)
        ```
- range
    - 左闭右开
    - 实践
        ```
        scala> Range(1,10)
        res2: scala.collection.immutable.Range = Range(1, 2, 3, 4, 5, 6, 7, 8, 9)
    
        scala> Range(1,10,2)
        res4: scala.collection.immutable.Range = Range(1, 3, 5, 7, 9)
        
        scala> Range(1,10,4)
        res5: scala.collection.immutable.Range = Range(1, 5, 9)
        
        scala> Range(10,1,-1)
        res6: scala.collection.immutable.Range = Range(10, 9, 8, 7, 6, 5, 4, 3, 2)
        
        # 第三个参数是步长
        ```
- until
    - 左闭右开
    - 实践
        ```
        scala> 1 until 10
        res7: scala.collection.immutable.Range = Range(1, 2, 3, 4, 5, 6, 7, 8, 9)

        scala> 1.until(10)
        res8: scala.collection.immutable.Range = Range(1, 2, 3, 4, 5, 6, 7, 8, 9)
        ```
- for
    - 实践
        ```
        for (i <- 1 to 10) {
          println(i)
        }
        
        val courses = Array("Hadoop", "Spark", "Zookeeper", "Kafka")
        for (course <- courses) {
          println(course)
        }
        
        val courses2 = Array("Hadoop", "Spark", "Zookeeper", "Kafka")
        courses2.foreach(course => println(course)) //遍历集合，course是集合中的每个元素，=>就是将左边的course作用上一个函数

        var (num, sum) = (100, 0)
        while(num > 0) {
          sum = sum + num
          num = num - 1
        }
        println(sum)
        ```

# 第四章 Scala面向对象

### 4-1 课程目录
- 面向对象概述
- 类定义与使用
- 构造器
- 继承与重写
- 抽象类
- 伴生类和伴生对象
- apply
- case class
- trait

### 4-2 面向对象概述
- Java/Scala OO
    - 封装：属性、方法封装到类中
    - 继承：父类和子类之间的关系
    - 多态：父类引用指向子类对象

### 4-3 类的定义和使用
- 实践
    ```
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
    ```
    
### 4-4 4-5 4-6 主构造器和附属构造器、继承、重写
- 实践
    ```
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
    ```

4-7 抽象类
- 实践
    ```
    package com.imooc.scala.course04
    
    object AbstractApp {
      def main(args: Array[String]): Unit = {
        val student = new Student2()
        println(student.name)
        student.speak
      }
    }
    
    /**
     * - 抽象类只有定义没有实现
     * - 抽象类不能被实例化
     */
    abstract class Person2 {
      def speak
    
      val name:String
      val age:Int
    }
    
    class Student2 extends Person2 {
      // ???等价于空函数体{}
      //override def speak: Unit = ???
      override def speak: Unit = {
        println("speak")
      }
    
      override val name: String = "liy"
      override val age: Int = 18
    }
    ```

### 4-8 4-9 伴生类和伴生对象 apply方法
- 实践
    ```
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
    ```
    
### 4-10 case class
- 实践
    ```
    package com.imooc.scala.course04

    object CaseClassApp {
      def main(args: Array[String]): Unit = {
        println(Dog("wangcai").name)
      }
    }
    
    // case class 不用new
    // 通常用在模式匹配中
    case class Dog(name:String)
    ```
    
### 4-11 trait
- 类似Java中的接口
- 用法
    ```
    class xxx extends ATrait
    
    class SparkConf(loadDefaults: Boolean) extends Cloneable with Logging with Serializable {
    ```

# 第五章 Scala集合

### 5-1 课程目录
- 数组
- List
- Set
- Map
- Option&Some&None
- Tuple

### 5-2 5-3 定长数组 变长数据组
- 实践
    ```
    package com.imooc.scala.course05

    object ArrayApp extends App {
      // 定长数组
      // 方式1
      val a = new Array[String](5)
      println(a.length)
      a(1) = "hello"
      println(a(1))
    
      // 方式2
      val b = Array("hadoop", "spark", "storm")
      b(1) = "flink" // b 是val，引用不能改变，但是可以改其中的值
      println(b(1))
    
      val c = Array(1,2,3,4,5,6,7,8,9)
      println(c.sum)
      println(c.max)
      println(c.min)
      println(c.mkString)
      println(c.mkString(","))
      println(c.mkString(" "))
      println(c.mkString("<", "-", ">"))
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      // 可变数组
      val d = scala.collection.mutable.ArrayBuffer[Int]()
      println(d.length)
      d += 1
      d += 2
      d += (3,4,5)
      d ++= Array(6,7,8)
      println(d)
      d.insert(0, 0)
      println(d)
      d.remove(1)
      println(d)
      d.remove(0,3)
      println(d)
      d.trimEnd(2)
      println(d)
    
      // 可变转不可变
      println(d.toArray.mkString(","))
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      // 遍历
      for(i <- 0 until d.length) {
        println(d(i))
      }
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      for(ele <- d) {
        println(ele)
      }
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      // 逆序
      for (i <- (0 until d.length).reverse) {
        println(d(i))
      }
    }

    
    # 输出
    5
    hello
    flink
    45
    9
    1
    123456789
    1,2,3,4,5,6,7,8,9
    1 2 3 4 5 6 7 8 9
    <1-2-3-4-5-6-7-8-9>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    0
    ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8)
    ArrayBuffer(0, 1, 2, 3, 4, 5, 6, 7, 8)
    ArrayBuffer(0, 2, 3, 4, 5, 6, 7, 8)
    ArrayBuffer(4, 5, 6, 7, 8)
    ArrayBuffer(4, 5, 6)
    4,5,6
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    4
    5
    6
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    4
    5
    6
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    6
    5
    4

    ```
    
### 5-4 List Set
- 实践
    ```
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
    
      // Set 不可重复的
      val set = Set(1,2,2,1,4,3)
      println(set.mkString(" "))
    
      val set1 = scala.collection.mutable.Set[Int]()
      set1 += 1
      set1 += (1,1,2,2)
      println(set1.mkString(" "))
    }
    
    # 输出
    1
    List(2, 3, 4, 5)
    1
    2 1
    1 2 3
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    2 3 4 5 6 7 8 9
    9
    1
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    1 2 4 3
    1 2

    ```
    
### Map
- 实践
    ```
    package com.imooc.scala.course05

    import scala.collection.mutable
    
    object MapApp extends App{
    
      // 不可变Map
      val a = Map("liy" -> 18, "tanli" -> 17)
      // a("liy") = 20 不可变的Map不能赋值，报错：Error:(6, 3) value update is not a member of scala.collection.immutable.Map[String,Int]
      // a("lsi") = 40 报错
      println(a("liy"))
      println(a("tanli"))
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      // 可变Map
      val b = scala.collection.mutable.Map("liy" -> 18, "tanli" -> 17)
      b("liy") = 20
      println(b("liy"))
      // println(b("pk")) 报错：没有key
      println(b.getOrElse("pk", 9)) // 优雅，常用
      b("lisi") = 40
      println(b.mkString(","))
      b += ("wangwu" -> 4, "zhangsan" -> 5)
      println(b.mkString(","))
      b -= "wangwu"
      println(b.mkString(","))
    
      val c = mutable.HashMap[String, Int]()
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      // 遍历
      for ((key, value) <- b) {
        println(key + " : " + value)
      }
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      for (key <- b.keySet) {
        println(key + " : " + b.getOrElse(key, 9))
      }
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      for (value <- b.values) {
        println(value)
      }
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    
      for ((key, _) <- b) {
        println(key + " : " + b.getOrElse(key, 9))
      }
    
    }

    # 输出
    18
    17
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    20
    9
    lisi -> 40,tanli -> 17,liy -> 20
    lisi -> 40,zhangsan -> 5,tanli -> 17,liy -> 20,wangwu -> 4
    lisi -> 40,zhangsan -> 5,tanli -> 17,liy -> 20
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    lisi : 40
    zhangsan : 5
    tanli : 17
    liy : 20
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    lisi : 40
    zhangsan : 5
    tanli : 17
    liy : 20
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    40
    5
    17
    20
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    lisi : 40
    zhangsan : 5
    tanli : 17
    liy : 20
    ```

### 5-7 Option&Some&None
- 观察
    ```
    scala> val b = scala.collection.mutable.Map("liy" -> 18, "tanli" -> 17)
    b: scala.collection.mutable.Map[String,Int] = Map(tanli -> 17, liy -> 18)
    
    scala> b.get("liy")
    res1: Option[Int] = Some(18)
    
    scala> b.get("liy1")
    res2: Option[Int] = None
    ```
- 实践
    ```
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
    ```

### 5-8 Tuple
- 实践
    ```
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

    # 输出
    1
    2
    1
    2
    3
    4
    5
    localhost
    8080
    ```
    
# 第六章 模式匹配

### 6-1 课程目录
- 基本数据类型模式匹配
- Array模式匹配
- List模式匹配
- Scala异常处理
- case class模式匹配
- Some&None模式匹配

### 6-2至6-6 6-8 6-9 最基础的模式匹配 加条件的匹配 Array模式匹配 List模式匹配 类型匹配 case-class模式匹配 Some&None模式匹配
- 模式匹配
    - 类似Java中的switch case，但比这个强大的多
    - 语法
        ```
        变量 match {
            case value1 => 代码1
            case value2 => 代码2
            ......
            case _ => 代码N
        }
        ```
- 实践
    ```
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

    # 输出
    李老师...
    Excellent...
    Just so so...
    You need work harder...
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    You need work harder...
    Excellent...
    lisi, you are a good boy, but...
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    Hi, zhangsan
    Hi: lisi , wangwu
    Hi, zhangsan and other friends...
    Hi, everybody...
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    Hi, zhangsan
    Hi, lisi , zhangsan
    Hi, zhangsan , lisi
    Hi, zhangsan and other friends...
    Hi, everybody...
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    Int
    String
    other type
    (name,pk)
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    CTO name is: Liy , floor is:22
    Employee name is: zhangsan , floor is:2
    other
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    your grade is : B
    your grade is : A
    Sorry...

    ```

### 6-7 异常处理
- 实践
    ```
    package com.imooc.scala.course06

    object ExceptionApp extends App {
      val file = "test.txt"
    
      try {
        // open file
        // use file
    
        val i = 10/0
        println(i)
    
      } catch {
        case e:ArithmeticException => println("除数不能为0...")
        case e:Exception => println(e.getMessage)
      } finally {
        // 释放资源，一定能执行
        // close file
      }
    }
    ```
    
# 第七章 Scala函数的高级操作
### 7-1 课程目录
- 字符串高级操作
- 匿名函数
- curry函数
- 高阶函数
- 偏函数

### 7-2 字符串高级操作
- 实践
    ```
    package com.imooc.scala.course07

    object StringApp extends App {
    
      val s = "Hello"
      val name = "liy"
    
      //println(s + name) //low
      println(s"Hello: $name") // 插值
    
      val team = "AC Milan"
      println(s"Hello: $name, Welcome to $team")
    
      val b =
        """
          |这是一个多行字符串
          |hello
          |world
          |liy
          |""".stripMargin
      println(b)
    
      val c =
        s"""
          |这是一个多行字符串
          |hello
          |world
          |$name
          |""".stripMargin
      println(c)
    }
    
    # 输出
    Hello: liy
    Hello: liy, Welcome to AC Milan
    
    这是一个多行字符串
    hello
    world
    liy
    
    
    这是一个多行字符串
    hello
    world
    liy
    ```
    
### 7-3 7-4 7-5 匿名函数 currying函数 高阶函数
- 实践
    ```
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

    # 输出
    Hi, Liy
    11
    5
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    5
    5
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    1,2,3,4,5,6,7,8
    2,3,4,5,6,7,8,9
    2,3,4,5,6,7,8,9
    2,4,6,8,10,12,14,16
    3,6,9,12,15,18,21,24
    2
    4
    6
    8
    10
    12
    14
    16
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    10
    12
    14
    16
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    1
    2
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    36
    -34
    -34
    -4
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    90
    90
    98
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    8
    1
    36
    ~~~~~~~~~~~~~~~~~~~~~~~~~~
    List(1, 2),List(3, 4),List(5, 6)
    1,2,3,4,5,6
    List(2, 4),List(6, 8),List(10, 12)
    2,4,6,8,10,12
    ```
    
### 7-6 偏函数
- 实践
    ```
    package com.imooc.scala.course07

    import scala.util.Random
    
    /**
     * 偏函数：被包在花括号内没有match的一组case语句
     */
    object PartialFunctionApp {
      val names = Array("liy", "tan li", "liu yu")
      val name = names(Random.nextInt(names.length))
    
      name match {
        case "liy" => println("李老师...")
        case "tan li" => println("谈老师...")
        case _ => println("不知道...")
      }
    
      // def 函数名:PartialFunction[入参类型, 出参类型]
      def sayChinses:PartialFunction[String, String] = {
        case "liy" => "李老师..."
        case "tan li" => "谈老师..."
        case _ => "不知道..."
      }
    
      println(sayChinses("liy"))
    }
    ```

# 第八章 Scala隐式转换

### 8-2 隐式转换概述
- 需求：为一个已存在的类添加一个新的方法
    - Java：动态代理
    - Scala：隐式转换（双刃剑）

### 8-3 8-4 隐式转换实战 隐式封装切面
- 实战
    ```
    # ImpicitApp
    package com.imooc.scala.course08
    
    import java.io.File
    import ImplicitAspect._
    
    object ImplicitApp extends App {
    
      // 定义隐式转换函数即可
      // 已封装ImplicitApp中
      // implicit def man2superman(man:Man):Superman = new Superman(man.name)
      val man = new Man("Liy")
      man.fly() // 本来Man中是没有fly方法的
    
      // 已封装ImplicitApp中
      // implicit def fileToRichFile(file: File): RichFile = new RichFile(file)
      val file = new File("/Users/milesyli/hello.txt")
      val txt = file.read() // 本来File中是没有read方法的
      println(txt)
    
      println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
    }
    
    class Man(val name:String) {
      def eat(): Unit = {
        println(s"man [ $name ] eat ......")
      }
    }
    
    class Superman(val name:String) {
      def fly(): Unit = {
        println(s"superman [ $name ] fly ......")
      }
    }
    
    class RichFile(val file:File) {
      def read() = {
        scala.io.Source.fromFile(file.getPath).mkString
      }
    }
    
    
    # ImplicitAspect
    package com.imooc.scala.course08

    import java.io.File
    
    object ImplicitAspect extends App {
    
      implicit def man2superman(man:Man):Superman = new Superman(man.name)
      implicit def fileToRichFile(file: File): RichFile = new RichFile(file)
    }

    ```
    
### 8-5 隐式参数
- 定义
    - 在函数或方法中，定义一个implicit修饰的参数
    - Scala会尝试找到一个指定类型的，用implicit修饰的对象
    - 注入参数
    - 了解即可，不建议用
- 实践
    ```
    package com.imooc.scala.course08

    object ImplicitParam extends App {
    
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

    ```

### 隐式类
- 实践
    ```
    package com.imooc.scala.course08

    object ImplicitClassApp extends App {
    
      // 表示所有Int类型都有了add方法
      implicit class Calculator(x:Int) {
        def add(a:Int) = a + x
      }
      println(1.add(3)) // ok 4
    }

    ```
    
# 第九章 Scala操作数据

### 9-2 Scala读取文件及网络数据
- 实践
    ```
    package com.imooc.scala.course09

    import scala.io.Source
    
    object FileApp {
      def main(args: Array[String]): Unit = {
        //val file = Source.fromFile("/Users/milesyli/hello.txt")
        val file = Source.fromFile("/Users/milesyli/hello.txt")(scala.io.Codec.UTF8)
    
        def readLine() = {
          for (line <- file.getLines()) {
            println(line)
          }
        }
    
        //readLine
    
        def readChar(): Unit = {
          for (ele <- file) {
            println(ele)
          }
        }
    
        //readChar
    
        def readNet(): Unit = {
          val file = Source.fromURL("http://www.baidu.com")
          for (line <- file.getLines()) {
            println(line)
          }
        }
        readNet()
      }
    }

    ```
    
### 7-3 Scala读取MySQL数据
- 实践
    ```
    package com.imooc.scala.course09
    
    import java.sql.{Connection, DriverManager, Statement}
    
    object MySQLApp {
      def main(args: Array[String]): Unit = {
        val url = "jdbc:mysql://localhost:3306/mysql"
        val username = ""
        val password = ""
    
        var connection:Connection = null
        var statement:Statement = null
        try{
          classOf[com.mysql.jdbc.Driver]
          connection = DriverManager.getConnection(url, username, password)
          statement = connection.createStatement()
          val resultSet =  statement.executeQuery("select host, user from user")
    
          while (resultSet.next()) {
            val host = resultSet.getString("host")
            val user = resultSet.getString("user")
            println(s"$host, $user")
          }
        } catch {
          case e:Exception => e.printStackTrace()
        } finally {
          if (statement == null) {
            statement.close()
          }
          if (connection == null) {
            connection.close()
          }
        }
    
      }
    }

    ```
