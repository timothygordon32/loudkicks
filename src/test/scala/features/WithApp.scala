package features

import com.github.nscala_time.time.DurationBuilder
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.console._
import org.loudkicks.service.{InMemoryWalls, InMemoryTimeLines, TestTime}

trait WithApp {
  val time = TestTime()

  val thePresent = new DateTime

  def in(when: DateTime) = {
    time.now = when
    this
  }

  def thePast(elapsed: DurationBuilder): DateTime = thePresent - elapsed

  private val app = new ConsoleParser with AllCommands {

    lazy val timeSource = WithApp.this.time

    lazy val walls = InMemoryWalls()

    lazy val timeLines = InMemoryTimeLines(subscriber = Some(walls), timeSource)
  }

  val parse = app.parse _
}
