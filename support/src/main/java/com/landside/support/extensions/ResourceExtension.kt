package com.landside.support.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Int.toColor(resources: Resources): Int = ResourcesCompat.getColor(resources, this, null)
fun Int.toColorList(resources: Resources): ColorStateList? = ResourcesCompat.getColorStateList(resources, this, null)

fun Int.toResString(context: Context): String = context.getString(this)

fun Int.toFormatString(
  context: Context,
  vararg args: Any?
) = String.format(toResString(context), *args)

fun @receiver:DrawableRes Int.toDrawable(context: Context): Drawable? =
  ContextCompat.getDrawable(context, this)

fun Drawable.tint(colors: ColorStateList): Drawable {
  val wrappedDrawable = DrawableCompat.wrap(this.mutate())
  DrawableCompat.setTintList(wrappedDrawable, colors)
  return wrappedDrawable
}