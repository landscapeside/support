package com.landside.support.extensions

import java.util.Objects

fun List<String?>?.maxNullable(def: String = ""): String {
  if (this == null) {
    return def
  }
  val iterator = iterator()
  if (!iterator.hasNext()) return def
  var max = iterator.next()
  while (iterator.hasNext()) {
    val e = iterator.next()
    if (max != null && max < e ?: "") {
      max = e
    } else if (max == null) {
      max = e
    }
  }
  return max ?: def
}

fun List<String?>?.sortedNullableDescending(): List<String> {
  if (this == null){
    return listOf()
  }
  val nonNullList = this.map { it?:"" }
  return nonNullList.sortedDescending()
}

fun <T> MutableList<T>.removeFirstOrNull(predicate:(T)->Boolean):T?{
  Objects.requireNonNull(predicate)
  val each: Iterator<T> = iterator()
  while (each.hasNext()) {
    val item = each.next()
    if (predicate(item)) {
      remove(item)
      return item
    }
  }
  return null
}

fun <T> List<T>.limit(limit: Int) = if (size > limit) subList(0, limit) else this