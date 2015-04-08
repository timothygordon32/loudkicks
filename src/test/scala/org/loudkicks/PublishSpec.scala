package org.loudkicks

class PublishSpec extends UnitSpec {

  "Publish" when {
    "parsing a valid command line" should {
      "return a posted response for that user name and message" in {
        Publish parse "Alice -> I love the weather today" should
          contain(Posted(User("Alice"), Message("I love the weather today")))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in {
        Publish parse "Alice" should be(empty)
      }
    }
  }
}
