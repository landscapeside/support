package com.landside.support.helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Keyboard {
  fun hide(view: View) {
    var imm = view.context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }

  fun show(view: View) {
    val imm = view.context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
  }
}