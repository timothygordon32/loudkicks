package org.loudkicks.console

import org.loudkicks.service.{Posts, Walls, TimeLines, TimeSource}

trait AllCommands {
  self: ConsoleParser =>

  def timeSource: TimeSource

  def timeLines: TimeLines

  def posts: Posts

  def walls: Walls

  val commands = Seq(
    Publish(posts),
    ReadTimeLine(timeLines, timeSource),
    Follow(walls),
    ReadWall(walls, timeSource)
  )
}
