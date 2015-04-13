package org.loudkicks.service

import org.loudkicks.{User, Wall}

trait Walls extends PostSubscriber {
  def wall(user: User): Wall

  def follower(user: User, following: User): Set[User]
}
