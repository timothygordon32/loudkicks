package org.loudkicks.console

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._
import org.loudkicks.service.{TestTime, Walls}

class ReadWallCommandSpec extends UnitSpec {

  "ReadWallCommand" when {
    "executing" should {
      "output the posts for the wall" in {

        val present = new DateTime
        val alicePostedAt = present - 10.minute
        val bobPostedAt = present - 1.minute

        val walls = new Walls {
          def wall(user: User) = {
            user should be(Bob)
            Wall(List(
              Post(Bob, Message("Too cold for me."), bobPostedAt),
              Post(Alice, Message("I really like the weather today!"), alicePostedAt)
            ))
          }

          def posted(post: Post) = fail("No updating should occur")

          def follower(user: User, following: User) = fail("No following should be made")
        }
        val readWall = ReadWallCommand(Bob, walls, TestTime(present))

        inside(readWall.execute) { case Lines(lines) =>
          lines should be(Seq(
            "Bob - Too cold for me. (1 minute ago)",
            "Alice - I really like the weather today! (10 minutes ago)"
          ))
        }
      }
    }
  }
}
