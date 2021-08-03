package com.landside.support.extensions

import junit.framework.TestCase

class DateExtensionKtTest : TestCase() {

    fun testEarlyThan() {
        val origin = "2007-07-09"
        val other = "2007-07-08"
        assert(origin.earlyThan(other))
    }
}