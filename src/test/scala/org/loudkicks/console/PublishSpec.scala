package org.loudkicks.console

import org.joda.time.DateTime
import org.loudkicks._
import org.loudkicks.service._

class PublishSpec extends UnitSpec {

  "Publish" when {

    "parsing a valid command line" should {

      val postedAt = new DateTime
      val time = TestTime(postedAt)
      val subscriber = new TestSubscriber

      val publish = Publish(PostDistributor(Seq(subscriber), time))

      "return a posted response for that user name and message" in {
        publish parse "Alice -> I love the weather today" should
          contain(Posted(Post(Alice, Message("I love the weather today"), postedAt)))
      }

      "save the post" in {

        publish parse "Alice -> I love the weather today"

        subscriber.posts should contain (Post(Alice, Message("I love the weather today"), postedAt))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        val subscriber = new TestSubscriber

        val publish = Publish(PostDistributor(Seq(subscriber), TestTime()))

        publish parse "Alice" should be(empty)

        subscriber.posts should be(empty)
      }
    }
  }
}
