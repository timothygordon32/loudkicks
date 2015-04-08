package org.loudkicks

import org.scalatest.{Inside, WordSpec, Matchers}

class LineParserSpec extends WordSpec with Matchers with Inside {

  "NullLineParser" when {
    "parsing 'anything'" should {
      "have an empty response" in {
        NullLineParser parse "anything" should be(Empty)
      }
    }
  }

  "EchoParser" when {
    "parsing 'anything'" should {
      "echo back a line containing 'anything'" in {
        inside(EchoParser parse "anything") { case Lines(lines) =>
          lines should have size 1
          exactly(1, lines) should include("anything")
        }
      }
    }
  }
}
