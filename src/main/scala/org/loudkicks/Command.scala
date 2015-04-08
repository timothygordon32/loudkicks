package org.loudkicks

trait Command {
  def parse(line: String): Option[Response]
}

object Echo extends Command {
  def parse(line: String) = Some(Lines(Seq(s"Echo: $line")))
}