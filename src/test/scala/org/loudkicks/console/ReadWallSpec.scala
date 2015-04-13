package org.loudkicks.console

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._
import org.loudkicks.service.{TestTime, Walls}

class ReadWallSpec extends UnitSpec {

  "ReadWall" when {
    "parsing a invalid command line" should {
      "ignore it" in {
        val wall = new ReadWall {
          def timeSource = fail("Time should not be accessed")

          def walls = fail("Walls should not be accessed")
        }

        wall parse "Alice" should be(empty)
      }
    }

    "parsing a matched line" should {
      "output the posts for the wall" in {

        val present = new DateTime
        val alicePostedAt = present - 10.minute
        val bobPostedAt = present - 1.minute

        val wall = new ReadWall {
          def timeSource = TestTime(present)

          val walls = new Walls {
            def wall(user: User) = {
              user should be(Bob)
              Wall(Seq(
                Post(Bob, Message("Too cold for me."), bobPostedAt),
                Post(Alice, Message("I really like the weather today!"), alicePostedAt)
              ))
            }

            def update(post: Post) = fail("No updating should occur")

            def follower(user: User, following: User) = fail("No following should be made")
          }
        }

        inside(wall parse "Bob wall") { case Some(Lines(lines)) =>
          lines should be(Seq(
            "Bob - Too cold for me. (1 minute ago)",
            "Alice - I really like the weather today! (10 minutes ago)"
          ))
        }
      }
    }
  }
}