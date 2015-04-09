package org.loudkicks

trait TimeLines {
  def post(user: User, message: Message): Post

  def read(user: User): Seq[Post]
}
