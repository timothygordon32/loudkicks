package org.loudkicks

import com.github.nscala_time.time.Imports._

class WallSpec extends UnitSpec {

  "Wall" when {
    "adding an event to an empty list" should {
      "just hold that event" in {
        val now = new DateTime
        val postedNow = Post(Alice, Message("now"), now)

        Wall(List.empty).addRecent(postedNow) should
          be (Wall(List(postedNow)))
      }
    }

    "adding a new event to an existing list" should {
      "place the new earlier event after the existing later event" in {
        val now = new DateTime
        val earlier = now - 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedEarlier = Post(Bob, Message("earlier"), earlier)

        Wall(List(postedNow)).addRecent(postedEarlier) should
          be (Wall(List(postedNow, postedEarlier)))
      }

      "place the new later event before the earlier existing event" in {
        val now = new DateTime
        val later = now + 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedLater = Post(Charlie, Message("later"), later)

        Wall(List(postedNow)).addRecent(postedLater) should
          be (Wall(List(postedLater, postedNow)))
      }

      "place the new event after the existing later event and before the earlier existing event" in {
        val now = new DateTime
        val later = now + 1.second
        val earlier = now + 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedLater = Post(Charlie, Message("later"), later)
        val postedEarlier = Post(Bob, Message("earlier"), earlier)

        Wall(List(postedLater, postedEarlier)).addRecent(postedNow) should
          be (Wall(List(postedLater, postedNow, postedEarlier)))
      }
    }

    "combining" should {
      "commute for one empty operand" in {
        val now = new DateTime
        val postedNow = Post(Alice, Message("now"), now)

        Wall(List.empty) + Wall(List(postedNow)) should
          be (Wall(List(postedNow)))
        Wall(List(postedNow)) + Wall(List.empty) should
          be (Wall(List(postedNow)))
      }

      "commute for non-empty operands" in {
        val now = new DateTime
        val earlier = now - 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedEarlier = Post(Bob, Message("earlier"), earlier)

        Wall(List(postedNow)) + Wall(List(postedEarlier)) should
          be (Wall(List(postedNow, postedEarlier)))
        Wall(List(postedEarlier)) + Wall(List(postedNow)) should
          be (Wall(List(postedNow, postedEarlier)))
      }
    }
  }
}
