package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._

class InMemoryTimeLinesSpec extends UnitSpec {

  "Time lines" when {
    "empty" should {
      "report no posts for a user" in {
        val timeLines = InMemoryTimeLines()

        timeLines.read(Alice).posts should be(empty)
      }
    }

    "posted to by a user" should {
      val firstPostAt = new DateTime
      val secondPostAt = firstPostAt + 1.minute

      val time = TestTime()
      val timeLines = InMemoryTimeLines(time)

      timeLines.posted(Post(Alice, Message("First!"), firstPostAt))
      timeLines.posted(Post(Alice, Message("And again!"), secondPostAt))

      "not return posts for another user" in {
        val timeLine = timeLines.read(Bob)

        timeLine.posts should be(empty)
      }

      "return the posts for that user in reverse order" in {
        val timeLine = timeLines.read(Alice)

        timeLine.posts should have size 2
        timeLine.posts(0) should be(Post(Alice, Message("And again!"), secondPostAt))
        timeLine.posts(1) should be(Post(Alice, Message("First!"), firstPostAt))
      }
    }
  }
}
