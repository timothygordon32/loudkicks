package org.loudkicks

trait AllCommands {
  self: ConsoleParser =>

  def timeSource: TimeSource

  def timeLines: TimeLines

  val commands = Seq(
    new Publish {
      val timeLines = AllCommands.this.timeLines
    },
    new Read {
      def timeSource = AllCommands.this.timeSource

      val timeLines = AllCommands.this.timeLines
    })
}