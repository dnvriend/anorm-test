name := "anorm-test"

organization := "com.github.dnvriend"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.4"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.18"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.8"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.0"
libraryDependencies += "org.playframework.anorm" %% "anorm" % "2.6.0"
// https://playframework.github.io/anorm/AnormPostgres.html
libraryDependencies += "org.playframework.anorm" %% "anorm-postgres" % "2.6.0"
libraryDependencies += "com.zaxxer" % "HikariCP" % "2.7.6"
libraryDependencies += "com.typesafe" % "config" % "1.3.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

// testing configuration
fork in Test := true
parallelExecution := false