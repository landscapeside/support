package com.landside.support.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget
import com.landside.support.extensions.toBitmap
import com.landside.support.extensions.toCircle

class CropCircleTarget(private val target: ImageView): ImageViewTarget<Bitmap?>(target) {
  override fun setResource(resource: Bitmap?) {
    view.setImageBitmap(resource)
  }

  override fun setDrawable(drawable: Drawable?) {
    view.setImageBitmap(drawable?.toBitmap()?.toCircle())
  }
}