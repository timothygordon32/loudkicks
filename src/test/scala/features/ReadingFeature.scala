package features

import org.loudkicks._
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class ReadingFeature extends FeatureSpec with GivenWhenThen with Matchers {

  info("As a social networking user")
  info("I want to read each user's time line")
  info("So that I can see what they've been up to")

  feature("Read a user's time line") {

    scenario("Alice reads her own single posted message") {
      new WithApp {
        Given("Alice has posted 'I love the weather today'")
        app.parse("Alice -> I love the weather today") should
          be(Posted(Post(User("Alice"), Message("I love the weather today"))))

        When("Alice's time line is read")
        val timeLine = app.parse("Alice")

        Then("the post 'I love the weather today' should be listed")
        timeLine.lines should contain ("I love the weather today")
      }
    }

    scenario("Alice reads her own single posted message from 10 seconds ago") {
      new WithApp {
        Given("Alice has posted 'I love the weather today' ten seconds ago")
        app.parse("Alice -> I love the weather today") should
          be(Posted(Post(User("Alice"), Message("I love the weather today"))))

        When("Alice's time line is read")
        val timeLine = app.parse("Alice")

        Then("the post 'I love the weather today (10 seconds ago)' should be listed")
        pending
        timeLine.lines should contain ("I love the weather today (10 seconds ago)")
      }
    }
  }

  trait WithApp {
    val app = new ConsoleParser with AllCommands {
      lazy val timeLines = InMemoryTimeLines()
    }
  }
}
