package org.loudkicks.console

import org.loudkicks.service.{Walls, TimeSource, TimeLines, Posts}
import org.loudkicks.{Post, Message, User}

trait Command {
  def execute: Response
}

case class PublishCommand(user: User, message: Message, posts: Posts) extends Command {
  def post(user: User, message: Message) = posts.post(user, message)

  def execute = Posted(post(user, message))
}

case class ReadTimeLineCommand(user: User, timeLines: TimeLines, timeSource: TimeSource) extends Command with EventFormatting {
  def format(post: Post): String = s"${post.message.text} (${format(post.when)})"

  def execute = Lines(timeLines.read(user).posts.map(format))
}

case class FollowCommand(follower: User, following: User, walls: Walls) extends Command {
  def execute = Subscriber(follower, walls.follower(follower, following))
}

case class ReadWallCommand(user: User, walls: Walls, timeSource: TimeSource) extends Command with EventFormatting {
  def format(post: Post): String = s"${post.user.name} - ${post.message.text} (${format(post.when)})"

  def wall(user: User): Seq[Post] = walls.wall(user).posts

  def execute = Lines(wall(user).map(format))
}
