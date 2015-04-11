package org.loudkicks.service

import org.loudkicks.User

trait InMemoryWalls extends Walls {
  def timeLines: TimeLines

  def wall(user: User) = timeLines.read(user)
}

object InMemoryWalls {
  def apply(tl: TimeLines = InMemoryTimeLines()) = new InMemoryWalls {
    def timeLines = tl
  }
}