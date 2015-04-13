package org.loudkicks.console

import org.loudkicks.service.{Walls, TimeLines, TimeSource}

trait AllCommands {
  self: ConsoleParser =>

  def timeSource: TimeSource

  def timeLines: TimeLines

  def walls: Walls

  val commands = Seq(
    new Publish {
      val timeLines = AllCommands.this.timeLines
    },
    new ReadTimeLine {
      def timeSource = AllCommands.this.timeSource

      val timeLines = AllCommands.this.timeLines
    },
    new Follow {
      def walls = AllCommands.this.walls
    },
    new ReadWall {
      def timeSource = AllCommands.this.timeSource

      val walls = AllCommands.this.walls
    })
}
