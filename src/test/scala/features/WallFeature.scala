package features

import com.github.nscala_time.time.Imports._
import org.loudkicks.console.Posted
import org.loudkicks.{Message, User, Post}

class WallFeature extends AcceptanceSpec {

  info("As a social networking user")
  info("I want to see a wall of all my posts and of all the users I follow")
  info("So that I can see what me and my friends have been up to all in one place")

  scenario("User's own posts appear on their wall") {
    new WithApp {
      Given("Charlie has posted 'I'm in New York today! Anyone want to have a coffee?' 2 seconds ago")
      in(thePast(2.seconds))
      app.parse("Charlie -> I'm in New York today! Anyone want to have a coffee?") should
        be(Posted(Post(User("Charlie"), Message("I'm in New York today! Anyone want to have a coffee?"), thePast(2.seconds))))

      When("Charlie looks at his own wall")
      in(thePresent)
      val wall = app.parse("Charlie wall")

      Then("the wall shows the message 'Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)'")
      wall.lines should have size 1
      wall.lines should contain ("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)")
    }
  }
}
