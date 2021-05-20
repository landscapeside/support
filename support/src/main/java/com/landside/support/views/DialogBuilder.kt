package com.landside.support.views

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.view.View

class DialogBuilder(context: Context) : AlertDialog.Builder(context) {

  private var currentDialog:AlertDialog? = null

  override fun create(): AlertDialog {
    currentDialog = super.create()
    return currentDialog!!
  }

  fun setButtonOnClick(view:View,listener: OnClickListener):DialogBuilder{
    view.setOnClickListener {
      listener.onClick(currentDialog,-1)
    }
    return this
  }
}