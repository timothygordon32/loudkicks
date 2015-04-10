package org.loudkicks

import org.joda.time.DateTime

trait TimeSource {
  def now: DateTime
}

object SystemTimeSource extends TimeSource {
  def now = DateTimes.now
}