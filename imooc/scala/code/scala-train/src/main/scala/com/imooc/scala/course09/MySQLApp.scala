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
