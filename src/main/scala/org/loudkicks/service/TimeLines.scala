package org.loudkicks.service

import org.loudkicks.{Message, Post, User}

trait TimeLines {
  def post(user: User, message: Message): Post

  def read(user: User): Seq[Post]
}
