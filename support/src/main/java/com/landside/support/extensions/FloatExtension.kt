package com.landside.support.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.toScale(scale:Int = 1):String{
  val bigDecimal = BigDecimal(this)
  return bigDecimal.setScale(scale, RoundingMode.DOWN).toDouble().toString()
}