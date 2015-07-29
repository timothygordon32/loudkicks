package org.loudkicks.console

import org.loudkicks._
import org.loudkicks.service._

import scala.util.parsing.input.CharSequenceReader

class PublishParserSpec extends UnitSpec {

  "PublishParser" when {

    "parsing a valid command line" should {
      "return a posted response for that user name and message" in new TestPublishParser {
        publish("Alice -> I love the weather today").get should
          be(PublishCommand(Alice, Message("I love the weather today"), posts))
      }
    }

    "parsing a invalid command line" should {
      "ignore it" in new TestPublishParser {
        a [RuntimeException] should be thrownBy publish(new CharSequenceReader("Alice")).get
      }
    }
  }

  trait TestPublishParser extends PublishParser {
    val posts = PostDistributor(Seq.empty, TestTime())
  }
}
