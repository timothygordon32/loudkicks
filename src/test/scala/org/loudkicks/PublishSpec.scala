package org.loudkicks

import org.joda.time.DateTime

class PublishSpec extends UnitSpec {

  "Publish" when {

    "parsing a valid command line" should {

      var posts: Seq[Post] = Seq.empty

      val postedAt = new DateTime

      val publish = new Publish {
        val timeLines = new TimeLines {
          def post(user: User, message: Message) = {
            val post = Post(user, message, postedAt)
            posts = posts :+ post
            post
          }

          def read(user: User) = fail("Time lines should not be read")
        }
      }

      "return a posted response for that user name and message" in {
        publish parse "Alice -> I love the weather today" should
          contain(Posted(Post(User("Alice"), Message("I love the weather today"), postedAt)))
      }

      "save the post" in {

        publish parse "Alice -> I love the weather today"

        posts should contain (Post(User("Alice"), Message("I love the weather today"), postedAt))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        val publish = new Publish {
          def timeLines = fail("Time lines should not be accessed")
        }

        publish parse "Alice" should be(empty)
      }
    }
  }
}
