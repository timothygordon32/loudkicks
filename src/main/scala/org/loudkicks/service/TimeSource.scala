package org.loudkicks.service

import org.joda.time.DateTime
import org.loudkicks.DateTimes

trait TimeSource {
  def now: DateTime
}

object SystemTimeSource extends TimeSource {
  def now = DateTimes.now
}