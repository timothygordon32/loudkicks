package org.loudkicks

import org.scalatest.{Matchers, WordSpecLike}

class LineParserSpec extends WordSpecLike with Matchers {

  "NullLineParser" should {

    "ignore any input when it does not know how to parse anything" in {
      NullLineParser parse "anything" shouldBe Seq.empty
    }
  }
}
