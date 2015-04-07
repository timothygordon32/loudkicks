package org.loudkicks

import org.scalatest.{Matchers, WordSpecLike}

class ConsoleSpec extends WordSpecLike with Matchers {

  "Console" should {

    "ignore any input when it knows no commands" in {
      val console = new Console {}

      console parse "anything" shouldBe Seq.empty
    }
  }
}
