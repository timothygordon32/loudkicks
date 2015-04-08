package features

import org.loudkicks.{LineParser, Response}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class ReadingFeature extends FeatureSpec with GivenWhenThen with Matchers {

  info("As a social networking user")
  info("I want to read each user's time line")
  info("So that I can see what they've been up to")

  feature("Read a user's time line") {

    scenario("Read a time line for a user who has posted a single message") {
      pending

      Given("Alice has posted 'I love the weather today'")
      val app = new LineParser {
        def parse(line: String):Response = ???
      }
      app.parse("Alice -> I love the weather today") should be (Response(Seq.empty))

      When("Alice's time line is read")
      val timeLine = app.parse("Alice")

      Then("the post 'I love the weather today' should be listed")
      timeLine.lines should contain only "I love the weather today"
    }
  }
}
