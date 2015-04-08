package org.loudkicks

import org.scalatest.{WordSpec, Matchers}

class LineParserSpec extends WordSpec with Matchers {

  "NullLineParser" should {

    "ignore any input when it does not know how to parse anything" in {
      NullLineParser parse "anything" shouldBe Response(Seq.empty)
    }
  }
}
