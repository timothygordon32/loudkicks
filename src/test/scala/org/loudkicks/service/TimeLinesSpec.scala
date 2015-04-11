package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._

class TimeLinesSpec extends UnitSpec {

  "Time lines" when {
    "empty" should {
      "report no posts for a user" in {
        val timeLines = InMemoryTimeLines()

        timeLines.read(User("Alice")) should be(empty)
      }
    }

    "posted to by a user" should {

      val firstPostAt = new DateTime
      val secondPostAt = firstPostAt + 1.minute

      val timeSource = new TimeSource {
        var now = firstPostAt
      }
      val timeLines = InMemoryTimeLines(timeSource)
      timeLines.post(User("Alice"), Message("First!"))
      timeSource.now = secondPostAt
      timeLines.post(User("Alice"), Message("And again!"))

      "not return posts for another user" in {
        val timeLine = timeLines.read(User("Bob"))

        timeLine should be(empty)
      }

      "return the posts for that user in reverse order" in {
        val timeLine = timeLines.read(User("Alice"))

        timeLine should have size 2
        timeLine(0) should be(Post(User("Alice"), Message("And again!"), secondPostAt))
        timeLine(1) should be(Post(User("Alice"), Message("First!"), firstPostAt))
      }
    }
  }
}
