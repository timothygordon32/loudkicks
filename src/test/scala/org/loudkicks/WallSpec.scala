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
      val now = new DateTime
      val earlier = now - 1.second
      val postedNow = Post(Alice, Message("now"), now)
      val postedEarlier = Post(Bob, Message("earlier"), earlier)

      "commute" in {
        Wall(List(postedNow)) + TimeLine(List(postedEarlier)) should
          be (Wall(List(postedNow, postedEarlier)))
        Wall(List(postedEarlier)) + TimeLine(List(postedNow)) should
          be (Wall(List(postedNow, postedEarlier)))
      }

      "drop duplicate posts" in {
        Wall(List(postedNow, postedEarlier)) + TimeLine(List(postedEarlier)) should
          be (Wall(List(postedNow, postedEarlier)))

      }
    }
  }
}
