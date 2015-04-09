package org.loudkicks

import org.joda.time.{DateTime, DateTimeZone}

object DateTimeUtils {
  def now = DateTime.now(DateTimeZone.UTC)
}
