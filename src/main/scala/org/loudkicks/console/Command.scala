package org.loudkicks.console

import org.loudkicks.service.{Posts, TimeLines, TimeSource, Walls}
import org.loudkicks.{Message, Post, User}

trait Command {
  def parse(line: String): Option[Response]
}

case class Publish(posts: Posts) extends Command {
  val valid = "^(.*) -> (.*)$".r

  def parse(line: String) = line match {
    case valid(user, message) =>
      Some(Posted(post(User(user), Message(message))))
    case _ => None
  }

  def post(user: User, message: Message) = posts.post(user, message)
}

case class ReadTimeLine(timeLines: TimeLines, timeSource: TimeSource) extends Command with EventFormatting {
  val valid = "^([^ ]*)$".r

  def format(post: Post): String = s"${post.message.text} (${format(post.when)})"

  def parse(line: String) = line match {
    case valid(user) => Some(Lines(timeLines.read(User(user)).posts.map(format)))
    case _ => None
  }
}

case class Follow(walls: Walls) extends Command {
  val valid = "^(.*)? follows ([^ ]*)$".r

  def parse(line: String) = line match {
    case valid(user, following) =>
      val follower = User(user)
      Some(Subscriber(follower, walls.follower(follower, following = User(following))))
    case _ => None
  }
}

case class ReadWall(walls: Walls, timeSource: TimeSource) extends Command with EventFormatting {
  val valid = "^(.*)? wall$".r

  def wall(user: User): Seq[Post] = walls.wall(user).posts

  def format(post: Post): String = s"${post.user.name} - ${post.message.text} (${format(post.when)})"

  def parse(line: String) = line match {
    case valid(user) => Some(Lines(wall(User(user)).map(format)))
    case _ => None
  }
}
