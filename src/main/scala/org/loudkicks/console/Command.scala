package org.loudkicks.console

import org.loudkicks.service.TimeLines
import org.loudkicks.{Message, Post, User}

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

trait Read extends Command with EventFormatting {
  def timeLines: TimeLines

  def timeLine(user: User): Seq[Post] = timeLines.read(user)

  def format(post: Post): String = s"${post.message.text} (${format(post.when)})"

  def parse(line: String) = Some(Lines(timeLine(User(line)).map(format)))
}

trait Wall extends Command with EventFormatting {
  val valid = "^(.*)? wall$".r

  def timeLines: TimeLines

  def wall(user: User): Seq[Post] = timeLines.read(user)

  def format(post: Post): String = s"${post.user.name} - ${post.message.text} (${format(post.when)})"

  def parse(line: String) = line match {
    case valid(user) => Some(Lines(wall(User(user)).map(format)))
    case _ => None
  }
}
