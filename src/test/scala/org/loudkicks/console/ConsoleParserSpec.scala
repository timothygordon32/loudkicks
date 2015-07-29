package org.loudkicks.console

import org.loudkicks._

import scala.util.parsing.combinator._

class ConsoleParserSpec extends UnitSpec {

  "ConsoleParser" when {
    "has no parsers" should {
      "return None" in new ConsoleParser {
        val parsers = Seq.empty

        parse("anything") should be(Empty)
      }
    }

    "has a literal parser" should {
      "return a response to a match" in new TestConsoleParser {
        val parsers = Seq(literal)

        parse("anything") should be(Lines(Seq("accepted")))
      }
    }

    "with a literal parser" should {
      "return None with no match" in new TestConsoleParser {
        val parsers = Seq(literal)

        parse("something") should be(Empty)
      }
    }
  }

  trait TestConsoleParser extends ConsoleParser {
    def literal: Parser[Command] = "anything" ^^ { case _ =>
      new Command {
        def execute = Lines(Seq("accepted"))
      }
    }
  }
}
