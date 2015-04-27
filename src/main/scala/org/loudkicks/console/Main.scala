package org.loudkicks.console

import java.io.PrintWriter

import org.loudkicks.service.{PostDistributor, InMemoryWalls, InMemoryTimeLines, SystemTimeSource}

import scala.io.Source

object Main extends App with ConsoleApp with ConsoleParser with AllParsers {
  val prompt = "> "
  val input = Source.stdin.getLines()
  val output = new PrintWriter(System.out, true)

  lazy val timeSource = SystemTimeSource
  lazy val timeLines = InMemoryTimeLines()
  lazy val walls = InMemoryWalls(timeLines)
  lazy val posts = PostDistributor(Seq(timeLines, walls), timeSource)

  run()
}
