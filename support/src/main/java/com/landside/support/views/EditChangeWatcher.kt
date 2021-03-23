package com.landside.support.views

import android.text.Editable
import android.text.TextWatcher

open class EditChangeWatcher : TextWatcher {
  var invoker: (String) -> Unit = {}
  open fun onChange(invoker: (String) -> Unit) {
    this.invoker = invoker
  }

  override fun afterTextChanged(p0: Editable?) {
  }

  override fun beforeTextChanged(
    p0: CharSequence?,
    p1: Int,
    p2: Int,
    p3: Int
  ) {
  }

  override fun onTextChanged(
    s: CharSequence?,
    start: Int,
    before: Int,
    count: Int
  ) {
    invoker(s.toString())
  }
}