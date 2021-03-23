package com.landside.support.glide

import android.graphics.Bitmap
import android.graphics.Matrix
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * 项目名称：商标进度查询
 * 作者:sgm
 * 类描述:
 */
class RotateTransformation(
  private val rotateRotationAngle: Float
) : BitmapTransformation() {
  override fun transform(
    pool: BitmapPool,
    toTransform: Bitmap,
    outWidth: Int,
    outHeight: Int
  ): Bitmap {
    val matrix = Matrix()
    //  Matrix matrix = new Matrix();
    val height = toTransform.height
    val matrixValues = floatArrayOf(1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f)
    matrix.setValues(matrixValues)
    //若是matrix.postTranslate(0, height);//表示将图片上下倒置
    matrix.postTranslate(rotateRotationAngle, height * 2.toFloat())
    return Bitmap.createBitmap(
        toTransform,
        0,
        0,
        toTransform.width,
        toTransform.height,
        matrix,
        true
    )
  }

  override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    messageDigest.update(ID_BYTES)
  }

  companion object {
    private const val ID = "RotateTransformation"
    private val ID_BYTES =
      ID.toByteArray(Key.CHARSET)
  }
}