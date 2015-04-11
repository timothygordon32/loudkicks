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

      When("Charlie's wall is shown")
      in(thePresent)
      val wall = app.parse("Charlie wall")

      Then("the wall shows the message 'Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)'")
      wall.lines should have size 1
      wall.lines should contain ("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)")
    }
  }

  scenario("Following one other user will show both user's own and other user's posts on the their wall") {
    new WithApp {
      Given("Charlie has posted 'I'm in New York today! Anyone want to have a coffee?' 2 seconds ago")
      in(thePast(2.seconds))
      app.parse("Charlie -> I'm in New York today! Anyone want to have a coffee?") should
        be(Posted(Post(User("Charlie"), Message("I'm in New York today! Anyone want to have a coffee?"), thePast(2.seconds))))

      And("Alice has posted 'I love the weather today' 5 minutes ago")
      in(thePast(5.minutes))
      app.parse("Alice -> I love the weather today") should
        be(Posted(Post(User("Alice"), Message("I love the weather today"), thePast(5.minutes))))

      And("Charlie is following Alice")
      app.parse("Charlie follows Alice")

      When("Charlie's wall is shown")
      in(thePresent)
      val wall = app.parse("Charlie wall")

      Then("the wall should show two messages")
      pending
      wall.lines should have size 2
      And("the wall shows the first message 'Charlie has posted 'I'm in New York today! Anyone want to have a coffee?' (2 seconds ago)'")
      wall.lines(0) should be("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)")
      And("the wall shows the second message 'Alice - I love the weather today (5 minutes ago)'")
      wall.lines(1) should be("Alice - I love the weather today (5 minutes ago)")
    }
  }
}
