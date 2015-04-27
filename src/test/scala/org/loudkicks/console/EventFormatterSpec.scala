package org.loudkicks.console

import com.github.nscala_time.time.Imports._
import org.loudkicks.UnitSpec
import org.loudkicks.service.TestTime

class EventFormatterSpec extends UnitSpec {

  "Event" when {
    "1 second ago" should {
      "format to '1 second ago'" in new EventFormatting {
        val timeSource = TestTime()

        val oneSecondAgo = timeSource.now - 1.second

        format(oneSecondAgo) should be("1 second ago")
      }
    }

    "10 seconds ago" should {
      "format to '10 seconds ago'" in new EventFormatting {
        val timeSource = TestTime()

        val tenSecondsAgo = timeSource.now - 10.seconds

        format(tenSecondsAgo) should be("10 seconds ago")
      }
    }
  }
}
