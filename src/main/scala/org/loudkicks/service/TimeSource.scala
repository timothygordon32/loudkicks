package org.loudkicks.service

import org.joda.time.{DateTime, DateTimeZone}

trait TimeSource {
  def now: DateTime
}

object SystemTimeSource extends TimeSource {
  def now = DateTime.now(DateTimeZone.UTC)
}