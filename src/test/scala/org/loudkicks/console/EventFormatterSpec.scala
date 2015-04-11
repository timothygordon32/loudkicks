package org.loudkicks.console

import com.github.nscala_time.time.Imports._
import org.loudkicks.DateTimes._
import org.loudkicks.UnitSpec

class EventFormatterSpec extends UnitSpec {

  "Event" when {
    "1 second ago" should {
      "format to '1 second ago'" in {

        val reference = now
        val oneSecondAgo = reference - 1.second

        EventFormatter(from = reference) format oneSecondAgo should be("1 second ago")
      }
    }

    "10 seconds ago" should {
      "format to '10 seconds ago'" in {

        val reference = now
        val tenSecondsAgo = reference - 10.seconds

        EventFormatter(from = reference) format tenSecondsAgo should be("10 seconds ago")
      }
    }
  }
}
