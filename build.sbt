organization := "org.loudkicks"

name := "console"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.ocpsoft.prettytime" % "prettytime" % "3.2.7.Final",
  "com.github.nscala-time" %% "nscala-time" % "1.8.0",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
