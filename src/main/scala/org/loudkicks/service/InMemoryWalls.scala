package org.loudkicks.service

import org.loudkicks.{Post, User, Wall}

import scala.collection.mutable

trait InMemoryWalls extends Walls {
  private val walls: mutable.Map[User, Wall] = mutable.Map.empty.withDefaultValue(Wall(List.empty))
  private val followers: mutable.Map[User, Set[User]] = mutable.Map.empty.withDefaultValue(Set.empty)

  def timeLines: TimeLines

  def posted(post: Post) = {
    (followers(post.user) + post.user).map(follower => update(follower, post))
  }

  def update(user: User, post: Post): Wall = {
    val oldWall = walls(user)
    val newWall = oldWall addRecent post
    walls += user -> newWall
    newWall
  }

  def wall(user: User): Wall = walls(user)

  def follower(user: User, following: User): User = {
    val updatedFollowers = followers(following) + user
    followers += following -> updatedFollowers

    val newWall = wall(user) + timeLines.read(following)
    walls += user -> newWall

    following
  }
}

object InMemoryWalls {
  def apply(tl: TimeLines) = new InMemoryWalls {
    def timeLines = tl
  }
}