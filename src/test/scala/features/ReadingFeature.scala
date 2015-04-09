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
        timeLine.lines should contain only "I love the weather today"
      }
    }
  }

  trait WithApp {
    object InMemoryTimeLines extends TimeLines {
      private var forUser: Map[User, List[Post]] = Map.empty.withDefaultValue(List.empty)

      def post(user: User, message: Message): Post = {
        val postsForUser = Post(user, message) :: read(user)
        forUser = forUser + (user -> postsForUser)
        read(user).head
      }

      def read(user: User) = forUser(user)
    }

    val app = new ConsoleParser {
      val commands = Seq(
        new Publish {
          val timeLines = InMemoryTimeLines
        },
        new Read {
          def timeLines = InMemoryTimeLines
        })
    }
  }
}
