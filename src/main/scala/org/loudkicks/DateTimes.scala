package org.loudkicks

import java.util.Date

import org.joda.time.{DateTime, DateTimeZone}

object DateTimes {
  def now = DateTime.now(DateTimeZone.UTC)
}
