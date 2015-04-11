package features

import com.github.nscala_time.time.Imports._
import com.github.nscala_time.time.DurationBuilder
import org.joda.time.DateTime
import org.loudkicks.console.{AllCommands, ConsoleParser}
import org.loudkicks.service.{InMemoryTimeLines, TimeSource}

trait WithApp {
  val timeSource = new TimeSource {
    var now = new DateTime
  }

  val thePresent = new DateTime

  def timeIs(when: DateTime) { timeSource.now = when }

  def inThePast(elapsed: DurationBuilder): DateTime = thePresent - elapsed

  val app = new ConsoleParser with AllCommands {

    lazy val timeSource = WithApp.this.timeSource

    lazy val timeLines = InMemoryTimeLines(timeSource)
  }
}
