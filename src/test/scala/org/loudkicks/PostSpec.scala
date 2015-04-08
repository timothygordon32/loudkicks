package org.loudkicks

class PostSpec extends UnitSpec {

  "Post" when {
    "parsing a valid command line" should {
      "respond with a post for that user name and message" in {
        Post parse "Alice -> I love the weather today" should
          contain(Posted(User("Alice"), Message("I love the weather today")))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        Post parse "Alice" should be(empty)
      }
    }
  }
}
