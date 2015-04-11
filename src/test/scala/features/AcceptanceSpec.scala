package features

import org.loudkicks.TestUsers
import org.scalatest.{FeatureSpec, Matchers, GivenWhenThen}

abstract class AcceptanceSpec extends FeatureSpec with GivenWhenThen with Matchers with TestUsers
