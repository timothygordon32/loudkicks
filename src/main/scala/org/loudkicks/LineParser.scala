package org.loudkicks

trait Response {
  def lines: Seq[String]
}

case class Lines(lines: Seq[String]) extends Response

trait EmptyResponse extends Response {
  val lines = Seq.empty
}

case object Empty extends EmptyResponse

case object Posted extends EmptyResponse

trait LineParser {
  def parse(line: String): Response
}

object NullLineParser extends LineParser {
  def parse(line: String) = Empty
}

object EchoParser extends LineParser {
  def parse(line: String) = Lines(Seq(s"Echo: $line"))
}