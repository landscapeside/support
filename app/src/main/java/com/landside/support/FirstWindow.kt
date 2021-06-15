package com.landside.support

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout

class FirstWindow(context:Context,anchorView:View):PopupWindow() {

  init {
    contentView = LayoutInflater.from(context)
        .inflate(R.layout.window_first, null)
    setStyle()
    contentView.setOnClickListener {
      dismiss()
    }
    contentView.findViewById<View>(R.id.open_second).setOnClickListener {
      SecondWindow(context,anchorView).showAtLocation(anchorView,
          Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
    }
  }


  private fun setStyle() {
    this.width = RelativeLayout.LayoutParams.MATCH_PARENT
    this.height = RelativeLayout.LayoutParams.MATCH_PARENT
    this.isFocusable = false
    this.isOutsideTouchable = false
    val dw = ColorDrawable(-0x50000000)
    this.setBackgroundDrawable(dw)
  }
}