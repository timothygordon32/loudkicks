package org.loudkicks.service

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.{Message, Post, UnitSpec}

class PostDistributorSpec extends UnitSpec {

  "PostDistributor" when {
    "receiving an event" should {
      "notify subscribers" in {

        val firstPostAt = new DateTime
        val secondPostAt = firstPostAt + 1.minute
        val subscriber = new TestSubscriber
        val time = TestTime()
        val distributor = PostDistributor(Seq(subscriber), time)
        time.now = firstPostAt
        distributor.post(Alice, Message("First!"))
        time.now = secondPostAt
        distributor.post(Alice, Message("And again!"))

        subscriber.posts should be (Seq(
          Post(Alice, Message("First!"), firstPostAt),
          Post(Alice, Message("And again!"), secondPostAt))
        )
      }
    }
  }
}
