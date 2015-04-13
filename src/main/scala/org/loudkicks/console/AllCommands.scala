package org.loudkicks.console

import org.loudkicks.service.{Walls, TimeLines, TimeSource}

trait AllCommands {
  self: ConsoleParser =>

  def timeSource: TimeSource

  def timeLines: TimeLines

  def walls: Walls

  val commands = Seq(
    Publish(timeLines),
    ReadTimeLine(timeLines, timeSource),
    Follow(walls),
    ReadWall(walls, timeSource)
  )
}
