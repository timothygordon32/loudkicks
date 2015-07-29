package org.loudkicks.console

import org.loudkicks._
import org.loudkicks.service.{PostSubscriber, Walls}

class FollowCommandSpec extends UnitSpec {

  "FollowCommand" when {

    "parsing a valid command line" should {

      var bobFollowing: Set[User] = Set.empty

      val walls = new Walls with NoPostsShouldBeReceived {
        def wall(user: User) = fail("No wall should be accessed")

        def follower(user: User, following: User) = {
          user should be(Bob)
          bobFollowing = bobFollowing + following
          following
        }
      }

      "have one user follow another" in {
        FollowCommand(Bob, following = Alice, walls).execute should be (Subscriber(Bob, following = Alice))
        FollowCommand(Bob, following = Charlie, walls).execute should be (Subscriber(Bob, following = Charlie))
        bobFollowing should contain allOf(Alice, Charlie)
      }
    }
  }

  trait NoPostsShouldBeReceived extends PostSubscriber {
    def posted(post: Post) = fail("No posts should be received")
  }
}
