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
      Some(Posted(save(User(user), Message(message))))
    case _ => None
  }

  def save(user: User, message:Message): Post
}

trait Read extends Command {
  def posts(user: User): Seq[Post]

  def parse(line: String) = Some(Lines(posts(User(line)).map(_.message.text)))
}