package org.loudkicks

trait LineParser {
  def parse(line: String): Response
}

object NullLineParser extends LineParser {
  def parse(line: String) = Empty
}

object EchoParser extends LineParser {
  def parse(line: String) = Lines(Seq(s"Echo: $line"))
}