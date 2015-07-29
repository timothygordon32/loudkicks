package org.loudkicks

import org.scalatest.{Inside, Matchers, WordSpec}

import scala.util.parsing.input.CharSequenceReader
import scala.language.implicitConversions

abstract class UnitSpec extends WordSpec with Matchers with Inside with TestUsers {
  implicit def readerFromString(s: String) = new CharSequenceReader(s)
}
