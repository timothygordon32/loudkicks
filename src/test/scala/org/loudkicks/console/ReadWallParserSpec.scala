package org.loudkicks.console

import org.loudkicks._
import org.loudkicks.service._

class ReadWallParserSpec extends UnitSpec {

  "ReadTimeLinWallParser" when {

    "parsing a valid command line" should {
      "return a posted response for that user name and message" in new TestReadWallParser {
        wall("Alice wall").get should
          be(ReadWallCommand(Alice, walls, timeSource))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in new TestReadWallParser {
        a [RuntimeException] should be thrownBy wall("blah blah").get
      }
    }
  }

  trait TestReadWallParser extends ReadWallParser {
    val timeLines = InMemoryTimeLines()
    val walls = InMemoryWalls(timeLines)
    val timeSource = TestTime()
  }
}
