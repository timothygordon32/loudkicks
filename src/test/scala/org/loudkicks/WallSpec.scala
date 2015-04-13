package org.loudkicks

import com.github.nscala_time.time.Imports._

class WallSpec extends UnitSpec {

  "Wall" when {
    "adding an event to an empty list" should {
      "just hold that event" in {
        val now = new DateTime
        val postedNow = Post(Alice, Message("now"), now)

        Wall(Seq.empty).addRecent(postedNow) should
          be (Wall(Seq(postedNow)))
      }
    }

    "adding a new event to an existing list" should {
      "place the new earlier event after the existing later event" in {
        val now = new DateTime
        val earlier = now - 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedEarlier = Post(Bob, Message("earlier"), earlier)

        Wall(Seq(postedNow)).addRecent(postedEarlier) should
          be (Wall(Seq(postedNow, postedEarlier)))
      }

      "place the new later event before the earlier existing event" in {
        val now = new DateTime
        val later = now + 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedLater = Post(Charlie, Message("later"), later)

        Wall(Seq(postedNow)).addRecent(postedLater) should
          be (Wall(Seq(postedLater, postedNow)))
      }

      "place the new event after the existing later event and before the earlier existing event" in {
        val now = new DateTime
        val later = now + 1.second
        val earlier = now + 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedLater = Post(Charlie, Message("later"), later)
        val postedEarlier = Post(Bob, Message("earlier"), earlier)

        Wall(Seq(postedLater, postedEarlier)).addRecent(postedNow) should
          be (Wall(Seq(postedLater, postedNow, postedEarlier)))
      }
    }

    "combining" should {
      "commute for one empty operand" in {
        val now = new DateTime
        val postedNow = Post(Alice, Message("now"), now)

        Wall(Seq.empty) + Wall(Seq(postedNow)) should
          be (Wall(Seq(postedNow)))
        Wall(Seq(postedNow)) + Wall(Seq.empty) should
          be (Wall(Seq(postedNow)))
      }

      "commute for non-empty operands" in {
        val now = new DateTime
        val earlier = now - 1.second
        val postedNow = Post(Alice, Message("now"), now)
        val postedEarlier = Post(Bob, Message("earlier"), earlier)

        Wall(Seq(postedNow)) + Wall(Seq(postedEarlier)) should
          be (Wall(Seq(postedNow, postedEarlier)))
        Wall(Seq(postedEarlier)) + Wall(Seq(postedNow)) should
          be (Wall(Seq(postedNow, postedEarlier)))
      }
    }
  }
}
