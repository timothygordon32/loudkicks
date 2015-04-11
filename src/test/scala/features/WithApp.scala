package features

import com.github.nscala_time.time.DurationBuilder
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.console._
import org.loudkicks.service.{InMemoryTimeLines, TestTime}

trait WithApp {
  val time = TestTime()

  val timeLines = InMemoryTimeLines(time)

  val thePresent = new DateTime

  def in(when: DateTime) { time.now = when }

  def thePast(elapsed: DurationBuilder): DateTime = thePresent - elapsed

  val app = new ConsoleParser with AllCommands {

    lazy val timeSource = WithApp.this.time

    lazy val timeLines = InMemoryTimeLines(timeSource)
  }
}
