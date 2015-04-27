package org.loudkicks.console

import scala.util.parsing.combinator._

trait ConsoleParser extends RegexParsers {
  def parsers: Seq[Parser[Command]]

  def combine(ps: Seq[Parser[Command]]): Parser[Command] = {
    if (ps.isEmpty) failure("No command found")
    else ps.head | combine(ps.tail)
  }

  def parse(input: String): Response = parseAll(combine(parsers), input) match {
    case Success(command, _) => command.execute
    case _ => Empty
  }
}
