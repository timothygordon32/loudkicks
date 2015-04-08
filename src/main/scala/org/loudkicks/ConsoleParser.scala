package org.loudkicks

trait ConsoleParser {
  def commands: Seq[Command]

  def parse(line: String) =
    commands.foldLeft[Option[Response]](None) { (result, command) =>
      result orElse command.parse(line)
    } getOrElse Empty
}
