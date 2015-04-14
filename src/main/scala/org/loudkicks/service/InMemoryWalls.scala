package org.loudkicks.service

import org.loudkicks.{Post, User, Wall}

import scala.collection.mutable

trait InMemoryWalls extends Walls {
  private val walls: mutable.Map[User, Wall] = mutable.Map.empty.withDefaultValue(Wall(List.empty))
  private val following: mutable.Map[User, Set[User]] = mutable.Map.empty.withDefaultValue(Set.empty)

  def timeLines: TimeLines

  def posted(post: Post) = {
    (following(post.user) + post.user).map(follower => update(follower, post))
  }

  def update(user: User, post: Post): Wall = {
    val oldWall = walls(user)
    val newWall = oldWall addRecent post
    walls += user -> newWall
    newWall
  }

  def wall(user: User): Wall = walls(user)

  def follower(user: User, following: User): Set[User] = {
    val updatedFollowing = this.following(user) + following
    this.following += user -> updatedFollowing

    val newWall = wall(user) + timeLines.read(following)
    walls += user -> newWall

    updatedFollowing
  }
}

object InMemoryWalls {
  def apply(tl: TimeLines) = new InMemoryWalls {
    def timeLines = tl
  }
}