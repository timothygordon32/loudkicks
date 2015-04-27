package org.loudkicks.service

import org.loudkicks.Post

trait PostSubscriber {
  def posted(post: Post): Unit
}
