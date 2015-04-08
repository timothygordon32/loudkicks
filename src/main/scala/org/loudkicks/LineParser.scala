package org.loudkicks

case class Response(lines: Seq[String])

trait LineParser {
  def parse(line: String): Response
}

object NullLineParser extends LineParser {
  def parse(line: String) = Response(Seq.empty)
}

object EchoParser extends LineParser {
  def parse(line: String) = Response(Seq(s"Echo: $line"))
}