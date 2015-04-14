package org.loudkicks.console

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._
import org.loudkicks.service.{InMemoryTimeLines, TestTime, TimeLines}

class ReadTimeLineSpec extends UnitSpec {

  "ReadTimeLine" when {
    "parsing a invalid command line" should {
      "ignore it" in {
        val read = ReadTimeLine(InMemoryTimeLines(), TestTime())

        read parse "Alice unrecognised command" should be(empty)
      }
    }

    "parsing an unknown user" should {

      val timeLines = new TimeLines {

        def posted(post: Post) = fail("Should not make any posts")

        def read(user: User) = {
          user should be(Zed)
          TimeLine(List.empty)
        }
      }
      val read = ReadTimeLine(timeLines, TestTime())

      "respond with an empty list of messages" in {
        inside(read parse "Zed") { case Some(Lines(lines)) =>
          lines should be(empty)
        }
      }
    }

    "parsing an known user" should {
      val bob = Bob
      val present = new DateTime
      val firstPostAt = present - 10.minute
      val secondPostAt = present - 1.minute

      val timeLines = new TimeLines {
        def posted(post: Post) = fail("Should not make any posts")

        def read(user: User) = {
          user should be(bob)
          TimeLine(List(
            Post(bob, Message("Good game, though."), secondPostAt),
            Post(bob, Message("Damn! We lost!"), firstPostAt)
          ))
        }
      }

      val read = new ReadTimeLine(timeLines, TestTime())

      "respond with messages" in {
        inside(read parse "Bob") { case Some(Lines(lines)) =>
          lines should be(Seq(
            "Good game, though. (1 minute ago)",
            "Damn! We lost! (10 minutes ago)"
          ))
        }
      }
    }
  }
}
