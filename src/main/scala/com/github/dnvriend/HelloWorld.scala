package com.github.dnvriend

import anorm._
import com.typesafe.config.{Config, ConfigFactory}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

object DB {
  def toProps(config: Config): java.util.Properties = {
    val properties = new java.util.Properties
    config.entrySet.forEach((e) => properties.setProperty(e.getKey, config.getString(e.getKey)))
    properties
  }

  val config: HikariConfig = new HikariConfig(toProps(ConfigFactory.load().getObject("db").toConfig))
  val ds: HikariDataSource = new HikariDataSource(config)

  def withConnection[A](f: java.sql.Connection => A): A = {
    val conn: java.sql.Connection = ds.getConnection
    try f(conn) finally conn.close()
  }

  /**
    * Shutdown the DataSource and its associated pool.
    */
  def shutdown() = ds.close()
}

object Person {
  implicit val parser: RowParser[Person] = Macro.namedParser[Person]
}

final case class Person(name: String, age: Int)

object HelloWorld extends App {
  DB.withConnection { implicit conn =>
    SQL"""
      CREATE TABLE IF NOT EXISTS PERSON(
       name VARCHAR,
       age NUMERIC
      )
    """ executeUpdate()

    SQL"INSERT INTO person values ('dennis', 43)" executeUpdate()

    val xs: Seq[Person] = SQL"SELECT * FROM PERSON".as(Person.parser.*)
    xs foreach println
  }

  DB.shutdown()
}

