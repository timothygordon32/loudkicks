package org.loudkicks

case class TimeLine(posts: List[Post]) {
  def add(post: Post) = TimeLine(post :: posts)

  def head = posts.head
}
