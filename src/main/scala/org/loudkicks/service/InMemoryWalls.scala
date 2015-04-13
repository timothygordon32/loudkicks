package org.loudkicks.service

import org.loudkicks.{Post, User, Wall}

trait InMemoryWalls extends Walls {
  var following: Map[User, Set[User]] = Map.empty.withDefaultValue(Set.empty)
  var walls: Map[User, Wall] = Map.empty.withDefaultValue(Wall(Seq.empty))

  def update(post: Post) = following(post.user).map(update(_, post)) + update(post.user, post)

  def update(user: User, post: Post): Wall = {
    val oldWall = walls(user)
    val newWall = oldWall.addRecent(post)
    walls = walls + (user -> newWall)
    newWall
  }

  def wall(user: User): Wall = walls(user)

  def follower(user: User, following: User): Set[User] = {
    val updatedFollowing = this.following(user) + following
    this.following = this.following + (user -> updatedFollowing)

    val newWall = wall(user) + wall(following)
    walls = walls + (user -> newWall)

    updatedFollowing
  }
}

object InMemoryWalls {
  def apply() = new InMemoryWalls {}
}