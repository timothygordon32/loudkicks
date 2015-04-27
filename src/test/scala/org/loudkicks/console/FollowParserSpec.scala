package org.loudkicks.console

import org.loudkicks._
import org.loudkicks.service.{InMemoryTimeLines, InMemoryWalls}

class FollowParserSpec extends UnitSpec {

  "FollowParser" when {

    "parsing a valid command line" should {
      "have one user follow another" in new TestFollowParser {
        follow("Bob follows Alice").get should be (FollowCommand(Bob, following = Alice, walls))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in new TestFollowParser {
        a[RuntimeException] should be thrownBy follow("Alice").get
      }
    }
  }

  trait TestFollowParser extends FollowParser {
    val walls = InMemoryWalls(InMemoryTimeLines())
  }
}
