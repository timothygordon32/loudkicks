package org.loudkicks.console

import org.joda.time.DateTime
import org.loudkicks._
import org.loudkicks.service.{InMemoryTimeLines, TimeLines}

class PublishSpec extends UnitSpec {

  "Publish" when {

    "parsing a valid command line" should {

      var posts: Seq[Post] = Seq.empty

      val postedAt = new DateTime

      val timeLines = new TimeLines {
        def post(user: User, message: Message) = {
          val post = Post(user, message, postedAt)
          posts = posts :+ post
          post
        }

        def read(user: User) = fail("Time lines should not be read")
      }

      val publish = Publish(timeLines)

      "return a posted response for that user name and message" in {
        publish parse "Alice -> I love the weather today" should
          contain(Posted(Post(Alice, Message("I love the weather today"), postedAt)))
      }

      "save the post" in {

        publish parse "Alice -> I love the weather today"

        posts should contain (Post(Alice, Message("I love the weather today"), postedAt))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        val publish = Publish(InMemoryTimeLines(None))

        publish parse "Alice" should be(empty)
      }
    }
  }
}
