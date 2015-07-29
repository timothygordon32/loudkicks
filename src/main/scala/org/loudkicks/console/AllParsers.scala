package org.loudkicks.console

trait AllParsers extends ConsoleParser with PublishParser with ReadTimeLineParser with FollowParser with ReadWallParser {
  val parsers = Seq(
    publish,
    readTimeLine,
    follow,
    wall
  )
}
