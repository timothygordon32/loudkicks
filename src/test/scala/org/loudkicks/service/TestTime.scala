package org.loudkicks.service

import org.joda.time.DateTime

class TestTime(var now: DateTime) extends TimeSource

object TestTime {
  def apply(now: DateTime = new DateTime) = new TestTime(now)
}
