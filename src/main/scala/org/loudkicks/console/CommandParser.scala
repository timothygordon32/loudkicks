package org.loudkicks.console

import org.loudkicks.service.{Posts, TimeLines, TimeSource, Walls}
import org.loudkicks.{Message, User}

import scala.util.parsing.combinator.RegexParsers

trait CommandParser extends RegexParsers {
  val startOfLine = "^".r
  val endOfLine = "$".r

  def user: Parser[User] = "[^ ]*".r ^^ User

  def message: Parser[Message] = ".*".r ^^ Message
}

trait PublishParser extends CommandParser {
  def posts: Posts

  def publish: Parser[Command] = startOfLine ~> user ~ "->" ~ message <~ endOfLine ^^ {
    case user ~ "->" ~ message => PublishCommand(user, message, posts)
  }
}

trait ReadTimeLineParser extends CommandParser {
  def timeLines: TimeLines

  def timeSource: TimeSource

  def readTimeLine: Parser[Command] = startOfLine ~> user <~ endOfLine ^^ {
    case user => ReadTimeLineCommand(user, timeLines, timeSource)
  }
}

trait FollowParser extends CommandParser {
  def walls: Walls

  def follow: Parser[Command] = startOfLine ~> user ~ "follows" ~ user <~ endOfLine ^^ {
    case follower ~ "follows" ~ following => FollowCommand(follower, following, walls)
  }
}

trait ReadWallParser extends CommandParser {
  def walls: Walls

  def timeSource: TimeSource

  def wall: Parser[Command] = startOfLine ~> user ~ "wall" <~ endOfLine ^^ {
    case user ~ "wall" => ReadWallCommand(user, walls, timeSource)
  }
}
