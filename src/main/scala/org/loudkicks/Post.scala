package org.loudkicks

import org.joda.time.DateTime

case class Post(user: User, message: Message, when: DateTime)
