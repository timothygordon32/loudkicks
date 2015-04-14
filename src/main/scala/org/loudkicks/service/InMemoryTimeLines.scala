package org.loudkicks.service

import org.loudkicks.{Post, TimeLine, User}

import scala.collection.mutable

trait InMemoryTimeLines extends TimeLines {
  def timeSource: TimeSource

  private val timeLines: mutable.Map[User, TimeLine] = mutable.Map.empty.withDefaultValue(TimeLine(List.empty))

  def posted(post: Post) = {
    val postsForUser = read(post.user).add(post)
    timeLines += (post.user -> postsForUser)
  }

  def read(user: User) = timeLines(user)
}

object InMemoryTimeLines {
  def apply(ts: TimeSource = SystemTimeSource) = new InMemoryTimeLines {
    def timeSource = ts
  }
}