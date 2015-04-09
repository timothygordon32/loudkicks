package org.loudkicks

import java.io.PrintWriter

import scala.io.Source

object Main extends App with ConsoleApp with ConsoleParser with AllCommands {
  val prompt = "> "
  val input = Source.stdin.getLines()
  val output = new PrintWriter(System.out, true)

  lazy val timeLines = InMemoryTimeLines()

  run()
}
