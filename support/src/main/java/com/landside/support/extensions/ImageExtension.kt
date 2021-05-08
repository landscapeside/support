package com.landside.support.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.landside.support.compat.DirectoryProvider
import com.landside.support.helper.SysInfo
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Bitmap.save(context: Context): String {
  val file = File("${DirectoryProvider.getPicPath(context)}/${System.currentTimeMillis()}.jpg")
  if (!file.exists()) {
    try {
      file.createNewFile()
    } catch (e: IOException) {
      return ""
    }
  }
  val fileOs = FileOutputStream(file)
  compress(JPEG, 100, fileOs)
  fileOs.flush()
  fileOs.close()
  return file.absolutePath
}

fun Int.getWidth(context: Context): Int {
  val options = BitmapFactory.Options()
  options.inJustDecodeBounds = true
  BitmapFactory.decodeResource(context.resources, this, options)
  return options.outWidth
}

fun Int.getHeight(context: Context): Int {
  val options = BitmapFactory.Options()
  options.inJustDecodeBounds = true
  BitmapFactory.decodeResource(context.resources, this, options)
  return options.outHeight
}

fun Int.toBitmap(context: Context): Bitmap {
  return BitmapFactory.decodeResource(context.resources, this)
}

fun Bitmap.toCropScaledBitmap(
  targetWidth: Int = SysInfo.screenWidth,
  targetHeight: Int = SysInfo.screenHeight
): Bitmap? {
  val bitmapWidth = this.width
  val bitmapHeight = this.height
  if (bitmapHeight == 0) {
    return null
  }
  val widthRatio = targetWidth * 1.0f / bitmapWidth * 1.0f
  var scaleBitmap: Bitmap? = null
  scaleBitmap = try {
    Bitmap.createScaledBitmap(
        this, targetWidth, (bitmapHeight * widthRatio).toInt(), true
    ) //先根据宽度比，等比缩放，然后截取需要的部分
  } catch (e: Exception) {
    return null
  }
  val sourceWidth = scaleBitmap.width
  val soureHeight = scaleBitmap.height
  val needHeight =
    if (targetHeight > soureHeight) soureHeight else targetHeight //如果需要的高度比图片原图的高度大，则用原图高度，否则使用期望的高度
  var resultBitmap: Bitmap? = null
  try {
    resultBitmap = Bitmap.createBitmap(scaleBitmap, 0, 0, sourceWidth, needHeight)
    scaleBitmap.recycle()
  } catch (e: Exception) {
  }
  return resultBitmap
}

fun Bitmap.toScaled(ratio: Float):Bitmap{
  val matrix = Matrix()
  matrix.preScale(ratio, ratio)
  val newBM = Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
  if (newBM == this) {
    return newBM
  }
  recycle()
  return newBM
}

fun Bitmap.toScaled(
  newWidth: Int = -1,
  newHeight: Int = -1
):Bitmap{
  val scaleWidth = if (newWidth == -1) 1.0f else newWidth.toFloat() / width
  val scaleHeight = if (newHeight == -1) 1.0f else newHeight.toFloat() / height
  val matrix = Matrix()
  matrix.postScale(scaleWidth, scaleHeight) // 使用后乘
  val newBM = Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
  if (!isRecycled) {
    recycle()
  }
  return newBM
}