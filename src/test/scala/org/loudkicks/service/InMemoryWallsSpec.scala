package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.{Message, Post, UnitSpec, User}

class InMemoryWallsSpec extends UnitSpec {

  "Wall" when {
    "read" should {
      "be empty for an unknown user" in new TestInMemoryWalls {
        wall(Zed) should be(empty)
      }

      "list the user's own posts" in new TestInMemoryWalls {
        val postedAt = new DateTime()
        timeLines.post(Alice, Message("Here I am!"))

        val wallForAlice = wall(Alice)

        wallForAlice should have size  1
        wallForAlice(0) should be(Post(Alice, Message("Here I am!"), postedAt))
      }

      "list another user's posts" in new TestInMemoryWalls {
        val postedAt = new DateTime()
        timeLines.post(Alice, Message("Here I am!"))
        follower(Bob, following = Alice)

        val wallForBob = wall(Bob)

        wallForBob should have size  1
        wallForBob(0) should be(Post(Alice, Message("Here I am!"), postedAt))
      }

      "list user's and followed user's posts in reverse time order" in new TestInMemoryWalls {
        val alicePostedAt = new DateTime()
        time.now = alicePostedAt
        timeLines.post(Alice, Message("Here I am!"))

        val charliePostedAt = alicePostedAt + 30.seconds
        time.now = charliePostedAt
        timeLines.post(Charlie, Message("What's going on today?"))

        val bobPostedAt = alicePostedAt + 1.minute
        time.now = bobPostedAt
        timeLines.post(Bob, Message("I'm here too!"))

        follower(Charlie, following = Alice)
        follower(Charlie, following = Bob)

        val wallForCharlie = wall(Charlie)

        wallForCharlie should have size 3
        wallForCharlie(0) should be(Post(Bob, Message("I'm here too!"), bobPostedAt))
        wallForCharlie(1) should be(Post(Charlie, Message("What's going on today?"), charliePostedAt))
        wallForCharlie(2) should be(Post(Alice, Message("Here I am!"), alicePostedAt))
      }
    }
  }

  trait TestInMemoryWalls extends InMemoryWalls {
    val time = TestTime()
    val timeLines = InMemoryTimeLines(time)
  }
}
