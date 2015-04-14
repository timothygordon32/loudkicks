package org.loudkicks.service

import org.loudkicks.Post

class TestSubscriber extends PostSubscriber {
  var posts: Seq[Post] = Seq.empty

  def posted(post: Post) = {
    posts = posts :+ post
  }
}
