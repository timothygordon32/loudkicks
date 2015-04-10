package features

import com.github.nscala_time.time.DurationBuilder
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks._
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class ReadingFeature extends FeatureSpec with GivenWhenThen with Matchers {

  info("As a social networking user")
  info("I want to read each user's time line")
  info("So that I can see what they've been up to")

  feature("Read a user's time line") {

    scenario("Alice reads her own single posted message from 5 minutes ago") {
      new WithApp {
        Given("Alice has posted 'I love the weather today' 5 minutes ago")
        timeIs(inThePast(5.minutes))
        app.parse("Alice -> I love the weather today") should
          be(Posted(Post(User("Alice"), Message("I love the weather today"), inThePast(5.minutes))))

        When("Alice's time line is read")
        timeIs(thePresent)
        val timeLine = app.parse("Alice")

        Then("the post 'I love the weather today (5 minutes ago)' should be listed")
        timeLine.lines should contain ("I love the weather today (5 minutes ago)")
      }
    }
  }

  trait WithApp {
    val timeSource = new TimeSource {
      var now = new DateTime
    }

    val thePresent = new DateTime

    def timeIs(when: DateTime) { timeSource.now = when }

    def inThePast(elapsed: DurationBuilder): DateTime = thePresent - elapsed

    val app = new ConsoleParser with AllCommands {

      lazy val timeSource = WithApp.this.timeSource

      lazy val timeLines = InMemoryTimeLines(timeSource)
    }
  }
}
