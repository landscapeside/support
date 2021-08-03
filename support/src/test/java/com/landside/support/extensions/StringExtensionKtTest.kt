package com.landside.support.extensions

import junit.framework.TestCase

class StringExtensionKtTest: TestCase() {

  fun testBirthDayByIdCard() {
    assertEquals(
        "501402198905125133".birthDayByIdCard,
        "19890512"
    )
  }
}