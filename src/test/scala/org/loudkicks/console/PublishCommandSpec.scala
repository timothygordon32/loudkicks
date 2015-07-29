package org.loudkicks.console

import org.joda.time.DateTime
import org.loudkicks._
import org.loudkicks.service._

class PublishCommandSpec extends UnitSpec {

  "PublishCommand" when {
    "publishing the post" should {
      "save the post" in {

        val postedAt = new DateTime
        val time = TestTime(postedAt)
        val subscriber = new TestSubscriber

        val publish = PublishCommand(Alice, Message("I love the weather today"), PostDistributor(Seq(subscriber), time))

        publish.execute should be(Posted(Post(Alice, Message("I love the weather today"), postedAt)))

        subscriber.posts should contain(Post(Alice, Message("I love the weather today"), postedAt))
      }
    }
  }
}
