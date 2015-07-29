package features

import com.github.nscala_time.time.DurationBuilder
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.loudkicks.console._
import org.loudkicks.service.{PostDistributor, InMemoryWalls, InMemoryTimeLines, TestTime}

trait WithApp {
  val time = TestTime()

  val thePresent = new DateTime

  def in(when: DateTime) = {
    time.now = when
    this
  }

  def thePast(elapsed: DurationBuilder): DateTime = thePresent - elapsed

  private val app = new ConsoleParser with AllParsers {

    lazy val timeSource = WithApp.this.time

    lazy val timeLines = InMemoryTimeLines(timeSource)

    lazy val walls = InMemoryWalls(timeLines)

    lazy val posts = PostDistributor(Seq(timeLines, walls), timeSource)
  }

  val parse = app.parse _
}
