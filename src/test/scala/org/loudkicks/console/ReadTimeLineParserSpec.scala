package org.loudkicks.console

import org.loudkicks._
import org.loudkicks.service._

import scala.util.parsing.input.CharSequenceReader

class ReadTimeLineParserSpec extends UnitSpec {

  "ReadTimeLineParser" when {

    "parsing a valid command line" should {
      "return a posted response for that user name and message" in new TestReadTimeLineParser {
        readTimeLine(new CharSequenceReader("Alice")).get should
          be(ReadTimeLineCommand(Alice, timeLines, timeSource))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in new TestReadTimeLineParser {
        a[RuntimeException] should be thrownBy readTimeLine("blah blah").get
      }
    }
  }

  trait TestReadTimeLineParser extends ReadTimeLineParser {
    val timeLines = InMemoryTimeLines()
    val timeSource = TestTime()
  }
}

