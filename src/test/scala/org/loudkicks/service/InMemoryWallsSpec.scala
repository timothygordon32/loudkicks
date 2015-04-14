package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.{Message, Post, UnitSpec}

class InMemoryWallsSpec extends UnitSpec {

  "InMemoryWalls" when {
    "read" should {
      "be empty for an unknown user" in new TestInMemoryWalls {
        wall(Zed).posts should be(empty)
      }

      "list the user's own posts" in new TestInMemoryWalls {
        val postedAt = new DateTime()
        posted(Post(Alice, Message("Here I am!"), postedAt))

        val wallForAlice = wall(Alice)

        wallForAlice.posts should have size  1
        wallForAlice.posts(0) should be(Post(Alice, Message("Here I am!"), postedAt))
      }

      "list another user's posts" in new TestInMemoryWalls {
        val postedAt = new DateTime()
        posted(Post(Alice, Message("Here I am!"), postedAt))
        follower(Bob, following = Alice)

        val wallForBob = wall(Bob)

        wallForBob.posts should have size  1
        wallForBob.posts(0) should be(Post(Alice, Message("Here I am!"), postedAt))
      }

      "list user's and followed user's posts in reverse time order" in new TestInMemoryWalls {
        val alicePostedAt = new DateTime()
        posted(Post(Alice, Message("Here I am!"), alicePostedAt))

        val charliePostedAt = alicePostedAt + 30.seconds
        posted(Post(Charlie, Message("What's going on today?"), charliePostedAt))

        val bobPostedAt = alicePostedAt + 1.minute
        posted(Post(Bob, Message("I'm here too!"), bobPostedAt))

        follower(Charlie, following = Alice)
        follower(Charlie, following = Bob)

        val wallForCharlie = wall(Charlie)

        wallForCharlie.posts should have size 3
        wallForCharlie.posts(0) should be(Post(Bob, Message("I'm here too!"), bobPostedAt))
        wallForCharlie.posts(1) should be(Post(Charlie, Message("What's going on today?"), charliePostedAt))
        wallForCharlie.posts(2) should be(Post(Alice, Message("Here I am!"), alicePostedAt))
      }
    }
  }

  class TestInMemoryWalls extends InMemoryWalls {
    val timeLines = InMemoryTimeLines()

    override def posted(post: Post) = {
      timeLines.posted(post)
      super.posted(post)
    }
  }
}
