package org.loudkicks.service

import org.loudkicks.{Post, Message, User}

trait Posts {
  def timeSource: TimeSource

  def post(user: User, message: Message): Post
}
