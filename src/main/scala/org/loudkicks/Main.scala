package org.loudkicks

import java.io.PrintWriter

import scala.io.Source

object Main extends App with ConsoleApp {
  val prompt = "> "
  val input = Source.stdin.getLines()
  val output = new PrintWriter(System.out, true)

  def parse(line: String) = EchoParser.parse(line)

  run()
}
