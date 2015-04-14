package org.loudkicks.service

import org.loudkicks.{Post, TimeLine, User}

trait InMemoryTimeLines extends TimeLines {
  def timeSource: TimeSource

  private var timeLines: Map[User, TimeLine] = Map.empty.withDefaultValue(TimeLine(List.empty))

  def posted(post: Post) = {
    val postsForUser = read(post.user).add(post)
    timeLines = timeLines + (post.user -> postsForUser)
  }

  def read(user: User) = timeLines(user)
}

object InMemoryTimeLines {
  def apply(ts: TimeSource = SystemTimeSource) = new InMemoryTimeLines {
    def timeSource = ts
  }
}