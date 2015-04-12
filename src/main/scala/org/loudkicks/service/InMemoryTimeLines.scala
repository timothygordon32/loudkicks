package org.loudkicks.service

import org.loudkicks.{TimeLine, Message, Post, User}



trait InMemoryTimeLines extends TimeLines {
  def timeSource: TimeSource

  private var timeLines: Map[User, TimeLine] = Map.empty.withDefaultValue(TimeLine(List.empty))

  def post(user: User, message: Message): Post = {
    val postsForUser = read(user).add(Post(user, message, timeSource.now))
    timeLines = timeLines + (user -> postsForUser)
    read(user).head
  }

  def read(user: User) = timeLines(user)
}

object InMemoryTimeLines {
  def apply(ts: TimeSource = SystemTimeSource) = new InMemoryTimeLines {
    def timeSource = ts
  }
}