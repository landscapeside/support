package com.landside.support.extensions

import java.math.BigDecimal

fun Double.toScale(scale:Int = 1):String{
  val bigDecimal = BigDecimal(this)
  return bigDecimal.setScale(scale).toDouble().toString()
}