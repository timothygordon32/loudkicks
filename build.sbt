organization := "org.loudkicks"

name := "console"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.2" % "test")

lazy val app = (project in file("."))
  .enablePlugins(JavaAppPackaging)
