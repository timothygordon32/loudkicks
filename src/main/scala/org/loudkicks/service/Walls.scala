package org.loudkicks.service

import org.loudkicks.{User, Post}

trait Walls {
  def wall(user: User): Seq[Post]

  def follower(user: User, following: User): Set[User]
}
