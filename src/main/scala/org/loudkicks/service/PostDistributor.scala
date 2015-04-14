package org.loudkicks.service

import org.loudkicks.{Post, Message, User}

case class PostDistributor(subscribers: Seq[PostSubscriber], timeSource: TimeSource) extends Posts {
  def post(user: User, message: Message) = {
    val p = Post(user, message, timeSource.now)
    subscribers.map(_.posted(p))
    p
  }
}
