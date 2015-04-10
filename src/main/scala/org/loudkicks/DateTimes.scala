package org.loudkicks

import java.util.Date

import org.joda.time.{DateTime, DateTimeZone}

object DateTimes {
  implicit def toDate(dateTime: DateTime): Date = dateTime.toDate

  def now = DateTime.now(DateTimeZone.UTC)
}
