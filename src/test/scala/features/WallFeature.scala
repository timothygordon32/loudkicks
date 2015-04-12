package features

import com.github.nscala_time.time.Imports._
import org.loudkicks.console.{Following, Posted}
import org.loudkicks.{Message, Post}

class WallFeature extends AcceptanceSpec {

  info("As a social networking user")
  info("I want to see a wall of all my posts and of all the users I follow")
  info("So that I can see what me and my friends have been up to all in one place")

  scenario("User's own posts appear on their wall") {
    new WithApp {
      Given("Charlie has posted 'I'm in New York today! Anyone want to have a coffee?' 2 seconds ago")
      in(thePast(2.seconds))
      app.parse("Charlie -> I'm in New York today! Anyone want to have a coffee?") should
        be(Posted(Post(Charlie, Message("I'm in New York today! Anyone want to have a coffee?"), thePast(2.seconds))))

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
        be(Posted(Post(Charlie, Message("I'm in New York today! Anyone want to have a coffee?"), thePast(2.seconds))))

      And("Alice has posted 'I love the weather today' 5 minutes ago")
      in(thePast(5.minutes))
      app.parse("Alice -> I love the weather today") should
        be(Posted(Post(Alice, Message("I love the weather today"), thePast(5.minutes))))

      And("Charlie is following Alice")
      app.parse("Charlie follows Alice") should
        be(Following(Charlie, following = Set(Alice)))

      When("Charlie's wall is shown")
      in(thePresent)
      val wall = app.parse("Charlie wall")

      Then("the wall should show two messages")
      wall.lines should have size 2
      And("the wall shows the first message 'Charlie has posted 'I'm in New York today! Anyone want to have a coffee?' (2 seconds ago)'")
      wall.lines(0) should be("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)")
      And("the wall shows the second message 'Alice - I love the weather today (5 minutes ago)'")
      wall.lines(1) should be("Alice - I love the weather today (5 minutes ago)")
    }
  }

  scenario("Following two other users will show both user's own and other user's posts on the their wall") {
    new WithApp {
      Given("Charlie has posted 'I'm in New York today! Anyone want to have a coffee?' 15 seconds ago")
      in(thePast(15.seconds))
      app.parse("Charlie -> I'm in New York today! Anyone want to have a coffee?") should
        be(Posted(Post(Charlie, Message("I'm in New York today! Anyone want to have a coffee?"), thePast(15.seconds))))
      And("Alice has posted 'I love the weather today' 5 minutes ago")
      in(thePast(5.minutes))
      app.parse("Alice -> I love the weather today") should
        be(Posted(Post(Alice, Message("I love the weather today"), thePast(5.minutes))))
      And("Bob has posted 'Damn! We lost!' 2 minutes ago")
      in(thePast(2.minutes))
      app.parse("Bob -> Damn! We lost!") should
        be(Posted(Post(Bob, Message("Damn! We lost!"), thePast(2.minutes))))
      And("Bob has posted 'Good game though.' 1 minute ago")
      in(thePast(1.minute))
      app.parse("Bob -> Good game though.") should
        be(Posted(Post(Bob, Message("Good game though."), thePast(1.minute))))
      And("Charlie follows both Alice and Bob")
      app.parse("Charlie follows Alice")
      app.parse("Charlie follows Bob") should
        be(Following(Charlie, following = Set(Alice, Bob)))

      When("Charlie's wall is displayed")
      in(thePresent)
      val wall = app.parse("Charlie wall")

      Then("Charlie's wall should show 4 messages")
      wall.lines should have size 4
      And("the wall shows the first message 'Charlie - I'm in New York today! Anyone want to have a coffee?' (15 seconds ago)'")
      wall.lines(0) should be("Charlie - I'm in New York today! Anyone want to have a coffee? (15 seconds ago)")
      And("the wall shows the second message 'Bob - Good game though. (1 minute ago)'")
      wall.lines(1) should be("Bob - Good game though. (1 minute ago)")
      And("the wall shows the third message 'Bob - Damn! We lost! (2 minutes ago)")
      wall.lines(2) should be("Bob - Damn! We lost! (2 minutes ago)")
      And("the wall shows the fourth message 'Alice - I love the weather today (5 minutes ago)'")
      wall.lines(3) should be("Alice - I love the weather today (5 minutes ago)")
    }
  }
}
