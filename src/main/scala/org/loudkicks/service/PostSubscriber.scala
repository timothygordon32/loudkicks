package org.loudkicks.service

import org.loudkicks.{Wall, Post}

trait PostSubscriber {
  def posted(post: Post): Unit
}
