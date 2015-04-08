package org.loudkicks

trait Response {
  def lines: Seq[String]
}

case class Lines(lines: Seq[String]) extends Response

case object Empty extends Response {
  val lines = Seq.empty
}

trait LineParser {
  def parse(line: String): Response
}

object NullLineParser extends LineParser {
  def parse(line: String) = Empty
}

object EchoParser extends LineParser {
  def parse(line: String) = Lines(Seq(s"Echo: $line"))
}