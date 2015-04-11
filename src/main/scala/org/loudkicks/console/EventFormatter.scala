package org.loudkicks.console

import org.joda.time.DateTime
import org.loudkicks.DateTimes._
import org.loudkicks.service.TimeSource
import org.ocpsoft.prettytime.PrettyTime
import org.ocpsoft.prettytime.units.JustNow

case class EventFormatter(from: DateTime) {
  private val formatter = {
    val pt = new PrettyTime(from)
    pt.getUnit(classOf[JustNow]).setMaxQuantity(999)
    pt
  }

  def format(when: DateTime): String = formatter.format(when)
}

trait EventFormatting {
  def timeSource: TimeSource

  def format(time: DateTime): String = EventFormatter(from = timeSource.now).format(time)
}