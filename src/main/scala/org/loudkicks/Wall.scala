package org.loudkicks

import com.github.nscala_time.time.Imports._

import scala.collection.mutable

case class Wall(posts: List[Post]) {
  implicit val descendingTime = Ordering.fromLessThan[Post](_.when > _.when)

  def addRecent(post: Post): Wall = {
    val i = posts.indexWhere(_.when > post.when)
    val (before, after) = posts.splitAt(i + 1)
    val newPosts = (before :+ post) ++ after
    Wall(newPosts)
  }

  def +(timeLine: TimeLine): Wall = {
    val sortedPosts = mutable.TreeSet.empty[Post]
    sortedPosts ++= posts
    sortedPosts ++= timeLine.posts

    Wall(sortedPosts.toList)
  }
}
