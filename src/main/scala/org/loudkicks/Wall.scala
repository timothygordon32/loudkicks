package org.loudkicks

import com.github.nscala_time.time.Imports._

case class Wall(posts: List[Post]) {
  def addRecent(post: Post): Wall = {
    val i = posts.indexWhere(_.when > post.when)
    val (before, after) = posts.splitAt(i + 1)
    val newPosts = (before :+ post) ++ after
    Wall(newPosts)
  }

  def +(that: Wall): Wall =
    if (this.posts.isEmpty) that
    else if (that.posts.isEmpty) this
    else {
      val unsortedPosts = this.posts ++ that.posts
      val posts = unsortedPosts.sortWith(_.when > _.when)
      Wall(posts)
    }
}
