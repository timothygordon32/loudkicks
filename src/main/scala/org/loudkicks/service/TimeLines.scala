package org.loudkicks.service

import org.loudkicks.{TimeLine, Message, Post, User}

trait TimeLines {
  def post(user: User, message: Message): Post

  def read(user: User): TimeLine
}
