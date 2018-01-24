package com.github.dnvriend

import java.util.Properties

import anorm._
import com.typesafe.config.{ Config, ConfigFactory }
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

object ConnPool {
  def toProps(config: Config): Properties = {
    val properties = new java.util.Properties
    config.entrySet.forEach((e) => properties.setProperty(e.getKey, config.getString(e.getKey)))
    properties
  }
  val config: HikariConfig = new HikariConfig(toProps(ConfigFactory.load().getObject("db").toConfig))
  val ds: HikariDataSource = new HikariDataSource(config)
  implicit def getConnection: java.sql.Connection = ds.getConnection
}

object Person {
  implicit val parser: RowParser[Person] = Macro.namedParser[Person]
}
final case class Person(name: String, age: Int)
object HelloWorld extends App {
  import ConnPool.getConnection
  SQL"""
      CREATE TABLE IF NOT EXISTS PERSON(
       name VARCHAR,
       age NUMERIC
      )
    """ executeUpdate ()

  SQL"INSERT INTO person values ('dennis', 43)" executeUpdate ()

  val xs: Seq[Person] = SQL"SELECT * FROM PERSON".as(Person.parser.*)
  xs foreach println
}

