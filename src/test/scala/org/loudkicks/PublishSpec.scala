package org.loudkicks

class PublishSpec extends UnitSpec {

  "Publish" when {


    "parsing a valid command line" should {

      var posts: Seq[Post] = Seq.empty

      val publish = new Publish {
        def save(user: User, message: Message) = {
          val post = Post(user, message)
          posts = posts :+ post
          post
        }
      }

      "return a posted response for that user name and message" in {
        publish parse "Alice -> I love the weather today" should
          contain(Posted(Post(User("Alice"), Message("I love the weather today"))))
      }

      "save the post" in {

        publish parse "Alice -> I love the weather today"

        posts should contain (Post(User("Alice"), Message("I love the weather today")))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        val publish = new Publish {
          def save(user: User, message: Message) = ???
        }

        publish parse "Alice" should be(empty)
      }
    }
  }
}
