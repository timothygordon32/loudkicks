package org.loudkicks.console

import org.joda.time.DateTime
import org.loudkicks.service.TimeSource
import org.ocpsoft.prettytime.PrettyTime
import org.ocpsoft.prettytime.units.JustNow

trait EventFormatting {
  def timeSource: TimeSource

  case class EventFormatter(from: DateTime) {
    val ResolutionAtSeconds = 999

    private val formatter = {
      val pt = new PrettyTime(from.toDate)
      pt.getUnit(classOf[JustNow]).setMaxQuantity(ResolutionAtSeconds)
      pt
    }

    def format(when: DateTime): String = formatter.format(when.toDate)
  }

  def format(time: DateTime): String = EventFormatter(from = timeSource.now).format(time)
}