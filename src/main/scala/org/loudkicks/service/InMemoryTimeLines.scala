package org.loudkicks.service

import org.loudkicks.{Message, Post, User}

trait InMemoryTimeLines extends TimeLines {
  def timeSource: TimeSource

  private var forUser: Map[User, List[Post]] = Map.empty.withDefaultValue(List.empty)

  def post(user: User, message: Message): Post = {
    val postsForUser = Post(user, message, timeSource.now) :: read(user)
    forUser = forUser + (user -> postsForUser)
    read(user).head
  }

  def read(user: User) = forUser(user)
}

object InMemoryTimeLines {
  def apply(ts: TimeSource = SystemTimeSource): InMemoryTimeLines = new InMemoryTimeLines {
    def timeSource = ts
  }
}