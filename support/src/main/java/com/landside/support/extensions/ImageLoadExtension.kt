package com.landside.support.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority.HIGH
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.landside.support.glide.RotateTransformation
import com.landside.support.glide.ScaleTransformation
import java.io.File

fun String.loadImage(
  context: Context,
  imgView: ImageView,
  @DrawableRes errImg: Int = -1,
  @DrawableRes emptyImg: Int = -1
) {
  if (this.isEmpty()) {
    if (emptyImg != -1) {
      Glide.with(context)
          .load(emptyImg)
          .into(imgView)
    }
    return
  }
  val glideLoader = Glide.with(context)
      .load(this)
  if (errImg != -1) {
    glideLoader.error(errImg)
        .placeholder(errImg)
        .into(imgView)
  } else {
    glideLoader.into(imgView)
  }
}

fun Bitmap.loadImage(
  context: Context,
  imgView: ImageView,
  @DrawableRes defaultImg: Int = -1
) {
  val glideLoader = Glide.with(context)
      .load(this)
  if (defaultImg != -1) {
    glideLoader.error(defaultImg)
        .placeholder(defaultImg)
        .into(imgView)
  } else {
    glideLoader.into(imgView)
  }
}

fun File.loadImage(
    context: Context,
    imgView: ImageView,
    @DrawableRes defaultImg: Int = -1
) {
    val glideLoader = Glide.with(context)
        .load(this)
    if (defaultImg != -1) {
        glideLoader.error(defaultImg)
            .placeholder(defaultImg)
            .into(imgView)
    } else {
        glideLoader.into(imgView)
    }
}

fun Int.loadImage(
  context: Context,
  imgView: ImageView
) {
  val glideLoader = Glide.with(context)
      .load(this)
  glideLoader.placeholder(imgView.drawable)
      .dontAnimate()
      .into(imgView)
}

fun Int.loadGif(
  context: Context,
  imgView: ImageView
){
  val options = RequestOptions()
      .centerCrop()
      .priority(HIGH)
      .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
  Glide.with(context)
      .load(this)
      .listener(object :RequestListener<Drawable>{
        override fun onLoadFailed(
          e: GlideException?,
          model: Any?,
          target: Target<Drawable>?,
          isFirstResource: Boolean
        ): Boolean {
          return false
        }

        override fun onResourceReady(
          resource: Drawable?,
          model: Any?,
          target: Target<Drawable>?,
          dataSource: DataSource?,
          isFirstResource: Boolean
        ): Boolean {
          return false
        }

      })
      .apply(options)
      .into(imgView)
}

fun String.loadImageRotated(
  context: Context,
  imgView: ImageView
) {
  val drawableCrossFadeFactory =
    DrawableCrossFadeFactory.Builder()
        .setCrossFadeEnabled(true)
        .build()
  Glide
      .with(context)
      .load(this)
      .transform(RotateTransformation(180f))
      .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
      .into(imgView)
}

fun String.loadScaleImage(
  context: Context,
  imgView: ImageView,
  @DrawableRes defaultImg: Int = -1
){
  val glideLoader = Glide.with(context)
      .asBitmap()
      .load(this)
  if (defaultImg != -1) {
    glideLoader.error(defaultImg)
        .placeholder(defaultImg)
        .into(ScaleTransformation(imgView))
  } else {
    glideLoader.into(ScaleTransformation(imgView))
  }
}

fun String.loadCenterCropImage(
  context: Context,
  imgView: ImageView,
  @DrawableRes defaultImg: Int = -1
){
  val glideLoader = Glide.with(context)
      .asBitmap()
      .load(this)
      .centerCrop()
  if (defaultImg != -1) {
    glideLoader.error(defaultImg)
        .placeholder(defaultImg)
        .into(imgView)
  } else {
    glideLoader.into(imgView)
  }
}