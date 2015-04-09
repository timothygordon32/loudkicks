package org.loudkicks

class TimeLinesSpec extends UnitSpec {

  "Time lines" when {
    "empty" should {
      "report no posts for a user" in {
        val timeLines = InMemoryTimeLines()

        timeLines.read(User("Alice")) should be(empty)
      }
    }

    "posted to by a user" should {

      val timeLines = InMemoryTimeLines()
      timeLines.post(User("Alice"), Message("First!"))
      timeLines.post(User("Alice"), Message("And again!"))

      "not return posts for another user" in {
        val timeLine = timeLines.read(User("Bob"))

        timeLine should be(empty)
      }

      "return the posts for that user in reverse order" in {
        val timeLine = timeLines.read(User("Alice"))

        timeLine should have size 2
        timeLine(0) should be(Post(User("Alice"), Message("And again!")))
        timeLine(1) should be(Post(User("Alice"), Message("First!")))
      }
    }
  }
}
