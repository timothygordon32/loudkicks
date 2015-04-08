package org.loudkicks

import org.scalatest.{Inside, Matchers, WordSpec}

class CommandSpec extends WordSpec with Matchers with Inside {

  "Echo" when {
    "parsing 'anything'" should {
      "echo back a response with a line containing 'anything'" in {
        inside(Echo parse "anything") { case Some(Lines(lines)) =>
          lines should have size 1
          exactly(1, lines) should include("anything")
        }
      }
    }
  }
}
