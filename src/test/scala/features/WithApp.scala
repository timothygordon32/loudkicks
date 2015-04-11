package features

import com.github.nscala_time.time.DurationBuilder
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.console._
import org.loudkicks.service.{InMemoryTimeLines, TimeSource}

trait WithApp {
  val timeSource = new TimeSource {
    var now = new DateTime
  }

  val timeLines = InMemoryTimeLines(timeSource)

  val thePresent = new DateTime

  def in(when: DateTime) { timeSource.now = when }

  def thePast(elapsed: DurationBuilder): DateTime = thePresent - elapsed

  val app = new ConsoleParser with AllCommands {

    lazy val timeSource = WithApp.this.timeSource

    lazy val timeLines = InMemoryTimeLines(timeSource)
  }
}
