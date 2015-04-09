package org.loudkicks

trait Command {
  def parse(line: String): Option[Response]
}

object Echo extends Command {
  def parse(line: String) = Some(Lines(Seq(s"Echo: $line")))
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
  def timeLine(user: User): Seq[Post]

  def parse(line: String) = Some(Lines(timeLine(User(line)).map(_.message.text)))
}