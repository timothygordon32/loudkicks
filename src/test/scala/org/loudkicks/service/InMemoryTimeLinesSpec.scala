package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._

class InMemoryTimeLinesSpec extends UnitSpec {

  "Time lines" when {
    "empty" should {
      "report no posts for a user" in {
        val timeLines = InMemoryTimeLines(subscriber = None)

        timeLines.read(Alice).posts should be(empty)
      }
    }

    "posted to by a user" should {

      var received: Seq[Post] = Seq.empty

      val firstPostAt = new DateTime
      val secondPostAt = firstPostAt + 1.minute

      val time = TestTime()
      val timeLines = InMemoryTimeLines(subscriber = Some(new PostSubscriber {
        def update(post: Post) = {
          received = received :+ post
          Set(Wall(received))
        }
      }), time)

      time.now = firstPostAt
      timeLines.post(Alice, Message("First!"))
      time.now = secondPostAt
      timeLines.post(Alice, Message("And again!"))

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

      "forward posts to subscriber" in {
        received should be(Seq(
          Post(Alice, Message("First!"), firstPostAt),
          Post(Alice, Message("And again!"), secondPostAt)))
      }
    }
  }
}
