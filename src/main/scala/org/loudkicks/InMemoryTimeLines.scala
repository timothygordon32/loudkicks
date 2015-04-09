package org.loudkicks

class InMemoryTimeLines extends TimeLines {
  private var forUser: Map[User, List[Post]] = Map.empty.withDefaultValue(List.empty)

  def post(user: User, message: Message): Post = {
    val postsForUser = Post(user, message) :: read(user)
    forUser = forUser + (user -> postsForUser)
    read(user).head
  }

  def read(user: User) = forUser(user)
}

object InMemoryTimeLines {
  def apply(): InMemoryTimeLines = new InMemoryTimeLines
}