package org.loudkicks.service

import org.loudkicks.{Wall, Post}

trait PostSubscriber {
  def update(post: Post): Set[Wall]
}
