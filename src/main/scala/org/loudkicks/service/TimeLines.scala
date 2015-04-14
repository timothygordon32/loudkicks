package org.loudkicks.service

import org.loudkicks.{TimeLine, User}

trait TimeLines extends PostSubscriber {
  def read(user: User): TimeLine
}
