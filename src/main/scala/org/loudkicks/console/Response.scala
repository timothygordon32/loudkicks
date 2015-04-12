package org.loudkicks.console

import org.loudkicks.{User, Post}

sealed trait Response {
  def lines: Seq[String]
}

case class Lines(lines: Seq[String]) extends Response

trait Empty extends Response {
  val lines = Seq.empty
}

case object Empty extends Empty

case class Posted(post: Post) extends Empty

case class Following(user: User, following: Set[User]) extends Empty
