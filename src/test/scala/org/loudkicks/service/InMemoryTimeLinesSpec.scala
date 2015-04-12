package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._

class InMemoryTimeLinesSpec extends UnitSpec {

  "Time lines" when {
    "empty" should {
      "report no posts for a user" in {
        val timeLines = InMemoryTimeLines()

        timeLines.read(Alice) should be(empty)
      }
    }

    "posted to by a user" should {

      val firstPostAt = new DateTime
      val secondPostAt = firstPostAt + 1.minute

      val time = TestTime()
      val timeLines = InMemoryTimeLines(time)

      time.now = firstPostAt
      timeLines.post(Alice, Message("First!"))
      time.now = secondPostAt
      timeLines.post(Alice, Message("And again!"))

      "not return posts for another user" in {
        val timeLine = timeLines.read(Bob)

        timeLine should be(empty)
      }

      "return the posts for that user in reverse order" in {
        val timeLine = timeLines.read(Alice)

        timeLine should have size 2
        timeLine(0) should be(Post(Alice, Message("And again!"), secondPostAt))
        timeLine(1) should be(Post(Alice, Message("First!"), firstPostAt))
      }
    }
  }
}