package com.landside.support.extensions

import java.lang.StringBuilder

fun Int.limit(limit: Int = 999) = if (this > limit) {
  limit
} else {
  this
}

fun Int.limitString(limit: Int = 999) = if (this > limit) {
  "${limit}+"
} else {
  this.toString()
}

fun Int.min(min: Int) = if (this < min) {
  min
} else {
  this
}

fun Int?.ifNotNullOrZero(notNullResult: (Int) -> String): String = if (this == null || this == 0) {
  ""
} else {
  notNullResult(this)
}

fun Int.roundBy(size: Int): Int {
  if (size == 0) {
    return this
  }
  if (this % size == 0) {
    return (this / size + 1) * size
  }
  return (this / size + 2) * size
}

fun Int.triad(): String {
  val origin = toString()
  val result = StringBuilder()
  (origin.indices).forEachIndexed { index, _ ->
    result.append(origin[index])
    if ((origin.length - index) % 3 == 1 && (origin.length - index) > 1) {
      result.append(",")
    }
  }
  return result.toString()
}