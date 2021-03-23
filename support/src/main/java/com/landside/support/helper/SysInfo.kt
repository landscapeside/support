package com.landside.support.helper

import android.R.id
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import android.provider.Settings.Global
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import com.landside.support.extensions.toDip
import java.lang.reflect.Method

object SysInfo {
  private val metric = Resources.getSystem().displayMetrics
  val screenWidth = metric.widthPixels
  val screenHeight = metric.heightPixels
  val density = metric.density

  fun getStatusBarHeight(activity:Activity):Int{
    val rect = Rect()
    activity.window.decorView.getWindowVisibleDisplayFrame(rect)
    return rect.top
  }

  fun getNavigationHeight(context: Activity): Int {
    val windowheight: Int = context.windowManager.defaultDisplay
        .height //获取无导航栏状态栏时窗口高度
    var fullheigh = 0 //窗口总高度
    val display: Display = context.windowManager.defaultDisplay
    val dm = DisplayMetrics()
    val klass: Class<*>
    try {
      klass = Class.forName("android.view.Display")
      val method: Method = klass.getMethod("getRealMetrics", DisplayMetrics::class.java)
      method.invoke(display, dm)
      fullheigh = dm.heightPixels //获取窗口总高度
    } catch (e: Exception) {
      e.printStackTrace()
    }
    if (windowheight == fullheigh) return 0 //无虚拟导航栏存在
//    return fullheigh - windowheight - getStatusBarHeight(context) //导航栏高度
    Timber.d("===========>底部导航栏高度：${getNavigationBarHeight(context).toDip()}")
    if (fullheigh - windowheight- getStatusBarHeight(context) >0){
      return getNavigationBarHeight(context)
    }else {
      return 0
    }
  }
}

fun getNavigationBarHeightIfRoom(context: Context): Int {
  return if (navigationGestureEnabled(context)) {
    0
  } else getCurrentNavigationBarHeight(context as Activity)
}

/**
 * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
 *
 * @param context
 * @return
 */
fun navigationGestureEnabled(context: Context): Boolean {
  val `val` =
    Global.getInt(context.contentResolver, deviceInfo, 0)
  return `val` != 0
}

/**
 * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
 *
 * @return
 */
val deviceInfo: String
  get() {
    val brand = Build.BRAND
    if (TextUtils.isEmpty(brand)) return "navigationbar_is_min"
    return if (brand.equals("HUAWEI", ignoreCase = true)) {
      "navigationbar_is_min"
    } else if (brand.equals("XIAOMI", ignoreCase = true) || brand.equals("redmi",ignoreCase = true)) {
      "force_fsg_nav_bar"
    } else if (brand.equals("VIVO", ignoreCase = true)) {
      "navigation_gesture_on"
    } else if (brand.equals("OPPO", ignoreCase = true)) {
      "navigation_gesture_on"
    } else {
      "navigationbar_is_min"
    }
  }

/**
 * 非全面屏下 虚拟键实际高度(隐藏后高度为0)
 * @param activity
 * @return
 */
fun getCurrentNavigationBarHeight(activity: Activity): Int {
  return if (isNavigationBarShown(activity)) {
    getNavigationBarHeight(activity)
  } else {
    0
  }
}

/**
 * 非全面屏下 虚拟按键是否打开
 * @param activity
 * @return
 */
fun isNavigationBarShown(activity: Activity): Boolean {
  //虚拟键的view,为空或者不可见时是隐藏状态
  val view =
    activity.findViewById<View>(id.navigationBarBackground) ?: return false
  val visible = view.visibility
  return !(visible == View.GONE || visible == View.INVISIBLE)
}

/**
 * 非全面屏下 虚拟键高度(无论是否隐藏)
 * @param context
 * @return
 */
fun getNavigationBarHeight(context: Context): Int {
  var result = 0
  val resourceId = context.resources
      .getIdentifier("navigation_bar_height", "dimen", "android")
  if (resourceId > 0) {
    result = context.resources
        .getDimensionPixelSize(resourceId)
  }
  return result
}
