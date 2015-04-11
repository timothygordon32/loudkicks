package org.loudkicks.console

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._
import org.loudkicks.service.{TestTime, TimeLines}

class ReadSpec extends UnitSpec {

  "Read" when {
    "parsing an unknown user" should {

      val read = new Read {
        def timeSource = fail("Should not reference the time")

        val timeLines = new TimeLines {
          def post(user: User, message: Message) = fail("Should not make any posts")

          def read(user: User) = {
            user should be(Zed)
            Seq.empty
          }
        }
      }

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

      val read = new Read {
        def timeSource = TestTime()

        val timeLines = new TimeLines {
          def post(user: User, message: Message) = fail("Should not make any posts")

          def read(user: User) = {
            user should be(bob)
            Seq(
              Post(bob, Message("Good game, though."), secondPostAt),
              Post(bob, Message("Damn! We lost!"), firstPostAt)
            )
          }
        }
      }

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
