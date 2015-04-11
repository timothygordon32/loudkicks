package org.loudkicks.service

import org.loudkicks.{User, Post}

trait Walls {
  def wall(user: User): Seq[Post]
}
