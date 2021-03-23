package com.landside.support.extensions

import java.net.URI

fun URI.getQueryParameter(key:String):String {
  val pairs = query.split("&")
  pairs.forEach {
    val paramPair = it.split("=")
    val paramKey = paramPair.first()
    if (paramKey == key) {
      return paramPair.getOrNull(1)?:""
    }
  }
  return ""
}