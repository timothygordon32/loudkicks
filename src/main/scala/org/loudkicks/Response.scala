package org.loudkicks

sealed trait Response {
  def lines: Seq[String]
}

case class Lines(lines: Seq[String]) extends Response

trait EmptyResponse extends Response {
  val lines = Seq.empty
}

case object Empty extends EmptyResponse

case object Posted extends EmptyResponse
