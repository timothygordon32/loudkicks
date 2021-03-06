package features

import com.github.nscala_time.time.Imports._
import org.loudkicks._
import org.loudkicks.console.Posted

class ReadingFeature extends AcceptanceSpec {

  info("As a social networking user")
  info("I want to read each user's time line")
  info("So that I can see what they've been up to")

  feature("Read a user's time line") {

    scenario("Alice reads her own single posted message from 5 minutes ago")(new WithApp {

      Given("Alice has posted 'I love the weather today' 5 minutes ago")
      in(thePast(5.minutes)).parse("Alice -> I love the weather today") should
        be(Posted(Post(Alice, Message("I love the weather today"), thePast(5.minutes))))

      When("Alice's time line is read")
      val timeLine = in(thePresent).parse("Alice")

      Then("the post 'I love the weather today (5 minutes ago)' should be listed")
      timeLine.lines should contain("I love the weather today (5 minutes ago)")
    })

    scenario("Bob reads his own two messages posted 2 minutes and 1 minute ago")(new WithApp {

      Given("Bob has posted 'Damn! We lost!' 2 minutes ago")
      in(thePast(2.minutes)).parse("Bob -> Damn! We lost!") should
        be(Posted(Post(Bob, Message("Damn! We lost!"), thePast(2.minutes))))

      And("Bob has posted 'Good game though.' 1 minute ago")
      in(thePast(1.minute)).parse("Bob -> Good game though.") should
        be(Posted(Post(Bob, Message("Good game though."), thePast(1.minutes))))

      When("Bob's time line is read")
      val timeLine = in(thePresent).parse("Bob")

      Then("the time line should show two lines")
      timeLine.lines should have size 2
      And("the most recent post 'Good game though. (1 minute ago)' should be shown first")
      timeLine.lines(0) should be("Good game though. (1 minute ago)")
      And("the previous post 'Damn! We lost! (2 minutes ago)' should be shown next")
      timeLine.lines(1) should be("Damn! We lost! (2 minutes ago)")
    })
  }
}
