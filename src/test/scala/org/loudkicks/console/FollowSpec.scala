package org.loudkicks.console

import org.loudkicks._
import org.loudkicks.service.{PostSubscriber, Walls}

class FollowSpec extends UnitSpec {

  "Follow" when {

    "parsing a valid command line" should {

      var bobFollowing: Set[User] = Set.empty

      val walls = new Walls with NoPostsShouldBeReceived {
        def wall(user: User) = fail("No wall should be accessed")

        def follower(user: User, following: User) = {
          user should be(Bob)
          bobFollowing = bobFollowing + following
          bobFollowing
        }
      }
      val follow = Follow(walls)

      "have one user follow another" in {
        follow parse "Bob follows Alice" should contain (Subscriber(Bob, following = Set(Alice)))
        follow parse "Bob follows Charlie" should contain (Subscriber(Bob, following = Set(Alice, Charlie)))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        val walls = new Walls with NoPostsShouldBeReceived  {
          def wall(user: User) = fail("No wall should be accessed")

          def follower(user: User, following: User) = fail("No following should occur")
        }

        Follow(walls) parse "Alice" should be(empty)
      }
    }
  }

  trait NoPostsShouldBeReceived extends PostSubscriber {
    def posted(post: Post) = fail("No posts should be received")
  }
}
