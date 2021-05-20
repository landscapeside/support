package com.landside.support.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.landside.support.views.DialogBuilder
import com.landside.support.views.EditChangeWatcher

fun EditText.isEmpty() = TextUtils.isEmpty(text.toString())

inline fun EditText.ifEmpty(runner: () -> Unit) {
  if (isEmpty()) runner()
}

fun FragmentActivity.mountFragment(@IdRes resId: Int, fragment: Fragment) {
  val fm = supportFragmentManager
  val transaction = fm.beginTransaction()
  transaction.add(resId, fragment)
  transaction.commit()
}

fun FragmentActivity.replaceFragment(@IdRes resId: Int, fragment: Fragment) {
  val fm = supportFragmentManager
  val transaction = fm.beginTransaction()
  transaction.replace(resId, fragment)
  transaction.commit()
}

fun Fragment.mountFragment(@IdRes resId: Int, fragment: Fragment) {
  val fm = childFragmentManager
  val transaction = fm.beginTransaction()
  transaction.add(resId, fragment)
  transaction.commit()
}

fun Fragment.replaceFragment(@IdRes resId: Int, fragment: Fragment) {
  val fm = childFragmentManager
  val transaction = fm.beginTransaction()
  transaction.replace(resId, fragment)
  transaction.commit()
}

fun CheckBox.safeSetChecked(
  listener: CompoundButton.OnCheckedChangeListener,
  executor: () -> Boolean
) {
  setOnCheckedChangeListener(null)
  isChecked = executor()
  setOnCheckedChangeListener(listener)
}

fun EditText.safeSetText(
  watcher: TextWatcher,
  executor: () -> String
) {
  removeTextChangedListener(watcher)
  setText(executor())
  addTextChangedListener(watcher)
}

fun EditText.safeUpdateState(
  watcher: TextWatcher,
  executor: () -> Unit
) {
  removeTextChangedListener(watcher)
  executor()
  addTextChangedListener(watcher)
}

fun EditText.addTextChange(watcher: EditChangeWatcher.() -> Unit) {
  addTextChangedListener(EditChangeWatcher().apply { watcher() })
}

operator fun Intent.get(key: String): String = getStringExtra(key) ?: ""

operator fun Intent.set(
  key: String,
  value: String
) = putExtra(key, value)

fun TextView.setDrawLeft(@DrawableRes resId: Int) {
  setCompoundDrawablesWithIntrinsicBounds(
      resId.toDrawable(context), null, null, null
  )
}

fun TextView.setDrawLeft(drawable: Drawable?) {
  setCompoundDrawablesWithIntrinsicBounds(
      drawable, null, null, null
  )
}

fun TextView.setDrawRight(@DrawableRes resId: Int) {
  setCompoundDrawablesWithIntrinsicBounds(
      null, null, resId.toDrawable(context), null
  )
}

fun TextView.setDrawRight(drawable: Drawable?) {
  setCompoundDrawablesWithIntrinsicBounds(
      null, null, drawable, null
  )
}

fun TextView.setDrawTop(@DrawableRes resId: Int) {
  setCompoundDrawablesWithIntrinsicBounds(
      null, resId.toDrawable(context), null, null
  )
}

fun TextView.setDrawTop(drawable: Drawable?) {
  setCompoundDrawablesWithIntrinsicBounds(
      null, drawable, null, null
  )
}

fun TextView.setDrawBottom(@DrawableRes resId: Int) {
  setCompoundDrawablesWithIntrinsicBounds(
      null, null, null, resId.toDrawable(context)
  )
}

fun TextView.setDrawBottom(drawable: Drawable?) {
  setCompoundDrawablesWithIntrinsicBounds(
      null, null, null, drawable
  )
}

fun View.setMarginInLinear(
  left: Int? = null,
  top: Int? = null,
  right: Int? = null,
  bottom: Int? = null
) {
  val params = this.layoutParams as LinearLayout.LayoutParams
  params.leftMargin = left ?: params.leftMargin
  params.topMargin = top ?: params.topMargin
  params.rightMargin = right ?: params.rightMargin
  params.bottomMargin = bottom ?: params.bottomMargin
  this.layoutParams = params
}

fun View.setMarginInRelative(
  left: Int?,
  top: Int?,
  right: Int?,
  bottom: Int?
) {
  val params = this.layoutParams as RelativeLayout.LayoutParams
  params.leftMargin = left ?: params.leftMargin
  params.topMargin = top ?: params.topMargin
  params.rightMargin = right ?: params.rightMargin
  params.bottomMargin = bottom ?: params.bottomMargin
  this.layoutParams = params
}

fun View.shake(forever:Boolean = false) {
  val translateAnimation: Animation = TranslateAnimation(0f, 5f, 0f, 0f)
  translateAnimation.interpolator = CycleInterpolator(5f)
  translateAnimation.duration = 1000
  if (forever) {
    translateAnimation.repeatMode = Animation.RESTART
    translateAnimation.repeatCount = -1
  }
  clearAnimation()
  translateAnimation.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationRepeat(p0: Animation?) {

    }

    override fun onAnimationEnd(p0: Animation?) {

    }

    override fun onAnimationStart(p0: Animation?) {

    }

  })
  animation = translateAnimation
}

fun dialog(context: Context,initializer:DialogBuilder.()->Unit){
  val builder = DialogBuilder(context).apply {
    initializer()
  }
  builder.show()
}

