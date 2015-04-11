package org.loudkicks.console

import org.joda.time.DateTime
import org.loudkicks.{Post, Message, User}
import org.loudkicks.service.{TimeLines, TimeSource}

trait Command {
  def parse(line: String): Option[Response]
}

trait Publish extends Command {
  val valid = "^(.*) -> (.*)$".r

  def parse(line: String) = line match {
    case valid(user, message) =>
      Some(Posted(post(User(user), Message(message))))
    case _ => None
  }

  def timeLines: TimeLines

  def post(user: User, message: Message) = timeLines.post(user, message)
}

trait Read extends Command {
  def timeSource: TimeSource

  def timeLines: TimeLines

  def timeLine(user: User): Seq[Post] = timeLines.read(user)

  def format(post: Post): String = s"${post.message.text} (${when(post.when)})"

  def when(time: DateTime): String = EventFormatter(from = timeSource.now).format(time)

  def parse(line: String) = Some(Lines(timeLine(User(line)).map(format)))
}