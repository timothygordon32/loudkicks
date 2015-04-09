package org.loudkicks

trait AllCommands {
  self: ConsoleParser =>
  def timeLines: TimeLines
  val commands = Seq(
    new Publish {
      val timeLines = AllCommands.this.timeLines
    },
    new Read {
      val timeLines = AllCommands.this.timeLines
    })
}
