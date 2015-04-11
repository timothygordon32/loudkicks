package org.loudkicks.console

import org.joda.time.DateTime
import org.loudkicks.{Post, Message, User, UnitSpec}
import org.loudkicks.service.{TestTime, TimeSource, InMemoryTimeLines, InMemoryWalls}

class WallSpec extends UnitSpec {

  "Wall" when {
    "read" should {
      "be empty for an unknown user" in {
        InMemoryWalls().wall(User("Zed")) should be(empty)
      }

      "list the user's own posts" in {
        val postedAt = new DateTime()
        val time = TestTime(postedAt)
        val timeLines = InMemoryTimeLines(time)
        val walls = InMemoryWalls(timeLines)
        timeLines.post(User("Alice"), Message("Here I am!"))

        val wall = walls.wall(User("Alice"))

        wall should have size  1
        wall(0) should be(Post(User("Alice"), Message("Here I am!"), postedAt))
      }
    }
  }
}
