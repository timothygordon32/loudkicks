package org.loudkicks.console

import org.loudkicks.service.{Walls, TimeLines}
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
  val valid = "^([^ ]*)$".r

  def timeLines: TimeLines

  def timeLine(user: User): Seq[Post] = timeLines.read(user)

  def format(post: Post): String = s"${post.message.text} (${format(post.when)})"

  def parse(line: String) = line match {
    case valid(user) => Some(Lines(timeLine(User(line)).map(format)))
    case _ => None
  }
}

trait Follows extends Command {
  val valid = "^(.*)? follows ([^ ]*)$".r

  def walls: Walls

  def parse(line: String) = line match {
    case valid(user, following) => val follower = User(user)
      Some(Subscriber(follower, walls.follower(follower, following = User(following))))
    case _ => None
  }
}

trait Wall extends Command with EventFormatting {
  val valid = "^(.*)? wall$".r

  def walls: Walls

  def wall(user: User): Seq[Post] = walls.wall(user)

  def format(post: Post): String = s"${post.user.name} - ${post.message.text} (${format(post.when)})"

  def parse(line: String) = line match {
    case valid(user) => Some(Lines(wall(User(user)).map(format)))
    case _ => None
  }
}
