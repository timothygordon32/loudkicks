package org.loudkicks

import org.scalatest.{Matchers, WordSpec}

trait Command {
  def parse(line: String): Option[Response]
}

trait ConsoleParser extends LineParser {
  def commands: Seq[Command]

  def parse(line: String) =
    commands.foldLeft[Option[Response]](None) { (result, command) =>
      result orElse command.parse(line)
    } getOrElse Empty
}

class ConsoleParserSpec extends WordSpec with Matchers {

  object IgnoringCommand extends Command {
    def parse(line: String) = None
  }

  object AcceptingCommand extends Command {
    def parse(line: String) = Some(Lines(Seq("accepted")))
  }

  "ConsoleParser" when {
    "without any commands" should {
      "return empty response" in new ConsoleParser {
        val commands = Seq.empty

        parse("anything") should be(Empty)
      }
    }

    "with a command that ignores the input" should {
      "return an empty response" in new ConsoleParser {
        val commands = Seq(IgnoringCommand)

        parse("anything") should be(Empty)
      }
    }

    "with a command that accepts the input" should {
      "return the response of the only accepting command" in new ConsoleParser {
        val commands = Seq(AcceptingCommand)

        parse("anything") should be(Lines(Seq("accepted")))
      }

      "return the response of the first accepting command" in new ConsoleParser {
        val commands = Seq(IgnoringCommand, AcceptingCommand)

        parse("anything") should be(Lines(Seq("accepted")))
      }
    }
  }
}
