package com.landside.support.helper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View

object ViewShotHelper {
  fun getShot(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val c = Canvas(bitmap)
    c.drawColor(Color.WHITE)
    view.draw(c)
    return bitmap
  }
}