package org.loudkicks

import com.github.nscala_time.time.Imports._
import org.ocpsoft.prettytime.PrettyTime
import org.ocpsoft.prettytime.units.JustNow
import DateTimes._

case class EventFormatter(from: DateTime) {
  private val formatter = {
    val pt = new PrettyTime(from)
    pt.getUnit(classOf[JustNow]).setMaxQuantity(999)
    pt
  }

  def format(when: DateTime): String = formatter.format(when)
}
