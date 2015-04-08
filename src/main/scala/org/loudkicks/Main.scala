package org.loudkicks

import java.io.PrintWriter

import scala.io.Source

object Main extends App with ConsoleApp with ConsoleParser {
  val prompt = "> "
  val input = Source.stdin.getLines()
  val output = new PrintWriter(System.out, true)
  val commands = Seq(Echo)

  run()
}
