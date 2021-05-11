package com.landside.support.extensions

import android.view.View

fun Boolean.toVisibility(): Int = if (this) View.VISIBLE else View.GONE

fun Boolean.toShown(): Int = if (this) View.VISIBLE else View.INVISIBLE

fun Boolean.then(positive: () -> Unit): Boolean {
  if (this) {
    positive()
  }
  return this
}