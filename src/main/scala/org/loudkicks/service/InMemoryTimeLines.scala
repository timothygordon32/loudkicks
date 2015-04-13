package org.loudkicks.service

import org.loudkicks.{TimeLine, Message, Post, User}

trait InMemoryTimeLines extends TimeLines {
  def timeSource: TimeSource

  def postSubscriber: Option[PostSubscriber]

  private var timeLines: Map[User, TimeLine] = Map.empty.withDefaultValue(TimeLine(List.empty))

  def post(user: User, message: Message): Post = {
    val post = Post(user, message, timeSource.now)
    val postsForUser = read(user).add(post)
    timeLines = timeLines + (user -> postsForUser)

    postSubscriber.map(_.update(post))

    read(user).head
  }

  def read(user: User) = timeLines(user)
}

object InMemoryTimeLines {
  def apply(subscriber: Option[PostSubscriber], ts: TimeSource = SystemTimeSource) = new InMemoryTimeLines {
    def timeSource = ts

    def postSubscriber = subscriber
  }
}