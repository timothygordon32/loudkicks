package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.loudkicks.User

trait InMemoryWalls extends Walls {
  var following: Map[User, Set[User]] = Map.empty.withDefaultValue(Set.empty)

  def timeLines: TimeLines

  def wall(user: User) = (timeLines.read(user).posts ++ following(user).map(timeLines.read(_).posts).flatten).sortWith(_.when > _.when)

  def follower(user: User, following: User): Set[User] = {
    val updatedFollowing = this.following(user) + following
    this.following = this.following + (user -> updatedFollowing)
    updatedFollowing
  }
}

object InMemoryWalls {
  def apply(tl: TimeLines) = new InMemoryWalls {
    def timeLines = tl
  }
}