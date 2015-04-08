package org.loudkicks

trait Command {
  def parse(line: String): Option[Response]
}

object Echo extends Command {
  def parse(line: String) = Some(Lines(Seq(s"Echo: $line")))
}

object Publish extends Command {
  val valid = "^(.*) -> (.*)$".r

  def parse(line: String) = line match {
    case valid(user, message) => Some(Posted(User(user), Message(message)))
    case _ => None
  }
}

trait Read extends Command {
  def posts(user: User): Seq[Post]

  def parse(line: String) = Some(Lines(posts(User(line)).map(_.message.text)))
}