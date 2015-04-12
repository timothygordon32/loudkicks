package org.loudkicks.console

import java.io.{ByteArrayOutputStream, PrintStream, PrintWriter}

import org.loudkicks.UnitSpec

class ConsoleAppSpec extends UnitSpec {

  trait TestConsoleApp extends ConsoleApp {
    val outputCapture = new ByteArrayOutputStream()
    val output = new PrintWriter(new PrintStream(outputCapture))
  }

  "ConsoleApp" when {
    "started" should {
      "output a prompt initially" in new TestConsoleApp {
        val prompt = "> "
        val input = Seq.empty.toIterator

        def parse(line: String) = Empty

        run()

        outputCapture.toString should be("> ")
      }

      "output a prompt each time app is ready for input" in new TestConsoleApp {
        val prompt = "> "
        val input = Seq("anything").toIterator

        def parse(line: String) = Empty

        run()

        outputCapture.toString should be("> > ")
      }
    }

    "parsing a line of input" should {
      "output response from parsed input" in new TestConsoleApp {
        val prompt = ""
        val input = Seq("anything").toIterator

        def parse(line: String) = Lines(Seq("Output line 1", "Output line 2"))

        run()

        outputCapture.toString should be
        """Output line 1
          |Output line 2
          | """.stripMargin
      }
    }
  }
}
