package org.loudkicks

sealed trait Response {
  def lines: Seq[String]
}

case class Lines(lines: Seq[String]) extends Response

trait Empty extends Response {
  val lines = Seq.empty
}

case object Empty extends Empty

case class Posted(post: Post) extends Empty
