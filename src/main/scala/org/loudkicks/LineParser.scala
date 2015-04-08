package org.loudkicks

trait LineParser {
  def parse(line: String): Seq[String]
}

object NullLineParser extends LineParser {
  def parse(line: String) = Seq.empty
}