package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.{Message, Post, UnitSpec, User}

class InMemoryWallsSpec extends UnitSpec {

  "Wall" when {
    "read" should {
      "be empty for an unknown user" in new TestInMemoryWalls {
        wall(User("Zed")) should be(empty)
      }

      "list the user's own posts" in new TestInMemoryWalls {
        val postedAt = new DateTime()
        timeLines.post(User("Alice"), Message("Here I am!"))

        val wallForAlice = wall(User("Alice"))

        wallForAlice should have size  1
        wallForAlice(0) should be(Post(User("Alice"), Message("Here I am!"), postedAt))
      }

      "list another user's posts" in new TestInMemoryWalls {
        val postedAt = new DateTime()
        timeLines.post(User("Alice"), Message("Here I am!"))
        follower(User("Bob"), following = User("Alice"))

        val wallForBob = wall(User("Bob"))

        wallForBob should have size  1
        wallForBob(0) should be(Post(User("Alice"), Message("Here I am!"), postedAt))
      }

      "list user's and followed user's posts in reverse time order" in new TestInMemoryWalls {
        val alicePostedAt = new DateTime()
        time.now = alicePostedAt
        timeLines.post(User("Alice"), Message("Here I am!"))

        val charliePostedAt = alicePostedAt + 30.seconds
        time.now = charliePostedAt
        timeLines.post(User("Charlie"), Message("What's going on today?"))

        val bobPostedAt = alicePostedAt + 1.minute
        time.now = bobPostedAt
        timeLines.post(User("Bob"), Message("I'm here too!"))

        follower(User("Charlie"), following = User("Alice"))
        follower(User("Charlie"), following = User("Bob"))

        val wallForCharlie = wall(User("Charlie"))

        wallForCharlie should have size 3
        wallForCharlie(0) should be(Post(User("Bob"), Message("I'm here too!"), bobPostedAt))
        wallForCharlie(1) should be(Post(User("Charlie"), Message("What's going on today?"), charliePostedAt))
        wallForCharlie(2) should be(Post(User("Alice"), Message("Here I am!"), alicePostedAt))
      }
    }
  }

  trait TestInMemoryWalls extends InMemoryWalls {
    val time = TestTime()
    val timeLines = InMemoryTimeLines(time)
  }
}
