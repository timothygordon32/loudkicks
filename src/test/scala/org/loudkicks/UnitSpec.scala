package org.loudkicks

import org.scalatest.{Inside, Matchers, WordSpec}

abstract class UnitSpec extends WordSpec with Matchers with Inside with TestUsers
