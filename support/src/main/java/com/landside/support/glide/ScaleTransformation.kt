package com.landside.support.glide

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.RelativeLayout.LayoutParams
import com.bumptech.glide.request.target.ImageViewTarget

/**
 * Glide加载图片时，根据图片宽度等比缩放
 */
class ScaleTransformation(private val target: ImageView) : ImageViewTarget<Bitmap?>(
    target
) {
  override fun setResource(resource: Bitmap?) {
    view.setImageBitmap(resource)
    if (resource != null) { //获取原图的宽高
      val width = resource.width
      val height = resource.height
      //获取imageView的宽
      val imageViewWidth = target.width
      //计算缩放比例
      val sy = (imageViewWidth * 0.1).toFloat() / (width * 0.1).toFloat()
      //计算图片等比例放大后的高
      val imageHeight = (height * sy).toInt()
      //ViewGroup.LayoutParams params = target.getLayoutParams();
//params.height = imageHeight;
      val params = LayoutParams(
          LayoutParams.MATCH_PARENT, imageHeight
      ) //固定图片高度，记得设置裁剪剧中
      params.bottomMargin = 10
      target.layoutParams = params
    }
  }

}