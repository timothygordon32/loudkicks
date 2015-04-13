package org.loudkicks.console

import java.io.PrintWriter

import org.loudkicks.service.{InMemoryWalls, InMemoryTimeLines, SystemTimeSource}

import scala.io.Source

object Main extends App with ConsoleApp with ConsoleParser with AllCommands {
  val prompt = "> "
  val input = Source.stdin.getLines()
  val output = new PrintWriter(System.out, true)

  lazy val walls = InMemoryWalls()
  lazy val timeLines = InMemoryTimeLines(subscriber = Some(walls))
  lazy val timeSource = SystemTimeSource

  run()
}
