package org.loudkicks

import java.io.{PrintWriter, ByteArrayOutputStream, PrintStream}

import org.scalatest.{Matchers, WordSpec}

class ConsoleAppSpec extends WordSpec with Matchers {

  "ConsoleApp" should {

    "output a prompt each time app is ready for input" in {
      val outputCapture = new ByteArrayOutputStream()
      val app = new ConsoleApp {
        val prompt = "> "
        val output = new PrintWriter(new PrintStream(outputCapture))
        val input = Seq("anything").toIterator

        def parse(line: String) = Empty
      }

      app.run()

      outputCapture.toString should be ("> > ")
    }

    "output response from parsed input" in {
      val outputCapture = new ByteArrayOutputStream()
      val app = new ConsoleApp {
        val prompt = ""
        val output = new PrintWriter(new PrintStream(outputCapture))
        val input = Seq("anything").toIterator
        def parse(line: String) = Lines(Seq("Output line 1", "Output line 2"))
      }

      app.run()

      outputCapture.toString should be
        """Output line 1
          |Output line 2
          |""".stripMargin
    }
  }
}
