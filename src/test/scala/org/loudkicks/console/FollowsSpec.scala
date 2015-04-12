package org.loudkicks.console

import org.loudkicks._
import org.loudkicks.service.Walls

class FollowsSpec extends UnitSpec {

  "Follows" when {

    "parsing a valid command line" should {

      var bobFollowing: Set[User] = Set.empty

      val follows = new Follows {
        val walls = new Walls {
          def wall(user: User) = fail("No wall should be accessed")

          def follower(user: User, following: User) = {
            user should be(Bob)
            bobFollowing = bobFollowing + following
            bobFollowing
          }
        }
      }

      "have one user follow another" in {
        follows parse "Bob follows Alice" should contain (Following(Bob, following = Set(Alice)))
        follows parse "Bob follows Charlie" should contain (Following(Bob, following = Set(Alice, Charlie)))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        val follows = new Follows {
          val walls = new Walls {
            def wall(user: User) = fail("No wall should be accessed")

            def follower(user: User, following: User) = fail("No following should occur")
          }
        }

        follows parse "Alice" should be(empty)
      }
    }
  }
}
