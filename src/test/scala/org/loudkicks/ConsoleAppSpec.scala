package org.loudkicks

import java.io.{PrintWriter, ByteArrayOutputStream, PrintStream}

import org.scalatest.{Matchers, WordSpec}

class ConsoleAppSpec extends WordSpec with Matchers {

  "ConsoleApp" should {

    "output a prompt" in {
      val outputCapture = new ByteArrayOutputStream()
      val app = new ConsoleApp {
        val prompt = "> "
        val output = new PrintWriter(new PrintStream(outputCapture))
        val input = Seq("anything").toIterator

        def parse(line: String) = Response(Seq.empty)
      }

      app.run()

      outputCapture.toString shouldBe "> > "
    }

    "output response from parsed input" in {
      val outputCapture = new ByteArrayOutputStream()
      val app = new ConsoleApp {
        val prompt = ""
        val output = new PrintWriter(new PrintStream(outputCapture))
        val input = Seq("anything").toIterator
        def parse(line: String) = Response(Seq("Output line 1", "Output line 2"))
      }

      app.run()

      outputCapture.toString shouldBe
        """Output line 1
          |Output line 2
          |""".stripMargin
    }
  }
}
