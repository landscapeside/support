package com.landside.support.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
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
  val sourceWidth = scaleBitmap?.width ?: 0
  val soureHeight = scaleBitmap?.height ?: 0
  val needHeight =
    if (targetHeight > soureHeight) soureHeight else targetHeight //如果需要的高度比图片原图的高度大，则用原图高度，否则使用期望的高度
  var resultBitmap: Bitmap? = null
  try {
    resultBitmap = Bitmap.createBitmap(scaleBitmap!!, 0, 0, sourceWidth, needHeight)
    scaleBitmap.recycle()
  } catch (e: Exception) {
  }
  return resultBitmap
}

fun Bitmap.toCircle():Bitmap{
  var left = 0
  var top = 0
  var right = width
  var bottom = height
  var roundPx = (height / 2).toFloat()
  if (width > height) {
    left = (width - height) / 2
    top = 0
    right = left + height
    bottom = height
  } else if (height > width) {
    left = 0
    top = (height - width) / 2
    right = width
    bottom = top + width
    roundPx = (width / 2).toFloat()
  }

  val output = Bitmap.createBitmap(width, height, ARGB_8888)
  val canvas = Canvas(output)
  val color = -0xbdbdbe
  val paint = Paint()
  val rect = Rect(left, top, right, bottom)
  val rectF = RectF(rect)

  paint.isAntiAlias = true
  canvas.drawARGB(0, 0, 0, 0)
  paint.color = color
  canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
  paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
  canvas.drawBitmap(this, rect, rect, paint)
  return output
}

fun Bitmap.toScaled(ratio: Float): Bitmap {
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
): Bitmap {
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

fun File.toBitmap(): Bitmap =
  BitmapFactory.decodeFile(absolutePath)

fun Bitmap.toGrey(): Bitmap {
  // 创建目标灰度图像
  // 创建目标灰度图像
  var bmpGray: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
  // 创建画布
  // 创建画布
  val c = Canvas(bmpGray)
  val paint = Paint()
  val cm = ColorMatrix()
  cm.setSaturation(0F)
  val f = ColorMatrixColorFilter(cm)
  paint.colorFilter = f
  c.drawBitmap(this, 0f, 0f, paint)
  return bmpGray
}

fun Bitmap.toLineGrey(): Bitmap {
  //创建线性拉升灰度图像
  //创建线性拉升灰度图像
  var linegray: Bitmap? = null
  linegray = copy(Bitmap.Config.ARGB_8888, true)
  //依次循环对图像的像素进行处理
  //依次循环对图像的像素进行处理
  for (i in 0 until width) {
    for (j in 0 until height) {
      //得到每点的像素值
      val col: Int = getPixel(i, j)
      val alpha = col and -0x1000000
      var red = col and 0x00FF0000 shr 16
      var green = col and 0x0000FF00 shr 8
      var blue = col and 0x000000FF
      // 增加了图像的亮度
      red = (1.1 * red + 30).toInt()
      green = (1.1 * green + 30).toInt()
      blue = (1.1 * blue + 30).toInt()
      //对图像像素越界进行处理
      if (red >= 255) {
        red = 255
      }
      if (green >= 255) {
        green = 255
      }
      if (blue >= 255) {
        blue = 255
      }
      // 新的ARGB
      val newColor = alpha or (red shl 16) or (green shl 8) or blue
      //设置新图像的RGB值
      linegray!!.setPixel(i, j, newColor)
    }
  }
  return linegray!!
}

fun Bitmap.toBinary(): Bitmap {
  //创建二值化图像
  //创建二值化图像
  var binarymap: Bitmap = copy(Bitmap.Config.ARGB_8888, true)
  //依次循环，对图像的像素进行处理
  //依次循环，对图像的像素进行处理
  for (i in 0 until width) {
    for (j in 0 until height) {
      //得到当前像素的值
      val col = binarymap.getPixel(i, j)
      //得到alpha通道的值
      val alpha = col and -0x1000000
      //得到图像的像素RGB的值
      val red = col and 0x00FF0000 shr 16
      val green = col and 0x0000FF00 shr 8
      val blue = col and 0x000000FF
      // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
      var gray =
        (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
      //对图像进行二值化处理
      gray = if (gray <= 95) {
        0
      } else {
        255
      }
      // 新的ARGB
      val newColor = alpha or (gray shl 16) or (gray shl 8) or gray
      //设置新图像的当前像素值
      binarymap.setPixel(i, j, newColor)
    }
  }
  return binarymap
}

fun Bitmap.toDrawable(resources: Resources): Drawable {
  return BitmapDrawable(resources, this)
}

fun Drawable.toBitmap(): Bitmap? {
  when (this) {
    is BitmapDrawable -> {
      return bitmap
    }
    is NinePatchDrawable -> {
      val bitmap = Bitmap
          .createBitmap(
              getIntrinsicWidth(),
              getIntrinsicHeight(),
              ARGB_8888
          )
      val canvas = Canvas(bitmap)
      setBounds(
          0, 0, getIntrinsicWidth(),
          getIntrinsicHeight()
      )
      draw(canvas)
      return bitmap
    }
    else -> {
      return null
    }
  }
}