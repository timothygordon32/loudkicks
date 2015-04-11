package org.loudkicks.console

import java.io.PrintWriter

import org.loudkicks.service.{InMemoryWalls, InMemoryTimeLines, SystemTimeSource}

import scala.io.Source

object Main extends App with ConsoleApp with ConsoleParser with AllCommands {
  val prompt = "> "
  val input = Source.stdin.getLines()
  val output = new PrintWriter(System.out, true)

  lazy val timeLines = InMemoryTimeLines()
  lazy val walls = InMemoryWalls(timeLines)
  lazy val timeSource = SystemTimeSource

  run()
}
