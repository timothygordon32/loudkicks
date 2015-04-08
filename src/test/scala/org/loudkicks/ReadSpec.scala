package org.loudkicks

class ReadSpec extends UnitSpec {

  "Read" when {
    "parsing an unknown user" should {

      val read = new Read {
        def posts(user: User): Seq[Posting] = {
          user should be(User("Zed"))
          Seq.empty
        }
      }

      "respond with an empty list of messages" in {
        inside(read parse "Zed") { case Some(Lines(lines)) =>
          lines should be(empty)
        }
      }
    }

    "parsing an known user" should {
      val bob = User("Bob")

      val read = new Read {
        def posts(user: User): Seq[Posting] = {
          user should be(bob)
          Seq(
            Posting(bob, Message("Good game, though.")),
            Posting(bob, Message("Damn! We lost!"))
          )
        }
      }

      "respond with messages" in {
        inside(read parse "Bob") { case Some(Lines(lines)) =>
          lines should be(Seq(
            "Good game, though.",
            "Damn! We lost!"
          ))
        }
      }
    }
  }
}
