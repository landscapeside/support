package com.landside.support.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Vibrator
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.landside.support.layoutcontainer.PopupLayoutContainer
import com.landside.support.mvp.MVPBaseActivity
import com.landside.support.views.DialogBuilder
import com.landside.support.views.EditChangeWatcher
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.autoDisposable
import java.io.File

fun EditText.isEmpty() = TextUtils.isEmpty(text.toString())

inline fun EditText.ifEmpty(runner: () -> Unit) {
    if (isEmpty()) runner()
}

fun FragmentActivity.mountFragment(
    @IdRes resId: Int,
    fragment: Fragment
) {
    val fm = supportFragmentManager
    val transaction = fm.beginTransaction()
    transaction.add(resId, fragment)
    transaction.commit()
}

fun FragmentActivity.replaceFragment(
    @IdRes resId: Int,
    fragment: Fragment
) {
    val fm = supportFragmentManager
    val transaction = fm.beginTransaction()
    transaction.replace(resId, fragment)
    transaction.commit()
}

fun Fragment.mountFragment(
    @IdRes resId: Int,
    fragment: Fragment
) {
    val fm = childFragmentManager
    val transaction = fm.beginTransaction()
    transaction.add(resId, fragment)
    transaction.commit()
}

fun Fragment.replaceFragment(
    @IdRes resId: Int,
    fragment: Fragment
) {
    val fm = childFragmentManager
    val transaction = fm.beginTransaction()
    transaction.replace(resId, fragment)
    transaction.commit()
}

inline fun <reified T> FragmentActivity.findFragment(): T? {
    return supportFragmentManager.fragments.firstOrNull { it is T } as? T
}

inline fun <reified T> FragmentActivity.findFragments(): List<T> {
    return supportFragmentManager.fragments.filterIsInstance<T>()
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

fun View.shake(forever: Boolean = false) {
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

fun dialog(
    context: Context,
    initializer: DialogBuilder.() -> Unit
) {
    val builder = DialogBuilder(context).apply {
        initializer()
    }
    builder.show()
}

fun MVPBaseActivity<*, *>.requestPermissions(
    vararg permissions: String,
    onResult: (Boolean) -> Unit
) {
    RxPermissions(this)
        .request(*permissions)
        .autoDisposable(autoDisposeProvider)
        .subscribe { granted -> onResult(granted) }
}

fun ImageView.load(
    url: String,
    @DrawableRes errImg: Int = -1,
    @DrawableRes emptyImg: Int = -1
) {
    url.loadImage(context, this, errImg, emptyImg)
}

fun ImageView.load(
    url: String,
    width: Int,
    height: Int,
    @DrawableRes errImg: Int = -1,
    @DrawableRes emptyImg: Int = -1
) {
    url.loadImage(context, this, width, height, errImg, emptyImg)
}

fun ImageView.load(
    bitmap: Bitmap,
    @DrawableRes defaultImg: Int = -1
) {
    bitmap.loadImage(context, this, defaultImg)
}

fun ImageView.load(
    file: File,
    @DrawableRes defaultImg: Int = -1
) {
    file.loadImage(context, this, defaultImg)
}

fun ImageView.load(
    @DrawableRes resId: Int
) {
    resId.loadImage(context, this)
}

fun ImageView.loadGif(
    @DrawableRes resId: Int
) {
    resId.loadGif(context, this)
}

fun ImageView.loadRotated(
    url: String
) {
    url.loadImageRotated(context, this)
}

fun ImageView.loadScale(
    url: String,
    @DrawableRes defaultImg: Int = -1
) {
    url.loadScaleImage(context, this, defaultImg)
}

fun ImageView.loadCenterCrop(
    url: String,
    @DrawableRes defaultImg: Int = -1
) {
    url.loadCenterCropImage(context, this, defaultImg)
}

fun ImageView.loadCircle(
    url: String,
    @DrawableRes defaultImg: Int = -1
) {
    url.loadCircle(context, this, defaultImg)
}

fun View.popAsDown(@LayoutRes layoutId:Int,block:PopupLayoutContainer.(PopupWindow)->Unit){
    PopupWindow().apply {
        width = RelativeLayout.LayoutParams.WRAP_CONTENT
        height = RelativeLayout.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isOutsideTouchable = true
        val dw = ColorDrawable(-0x50000000)
        setBackgroundDrawable(dw)
        contentView = LayoutInflater.from(context)
            .inflate(layoutId, null)
        block.invoke(PopupLayoutContainer(contentView),this)
    }.showAsDropDown(this)
}

@SuppressLint("MissingPermission")
fun AppCompatActivity.beep(){
    (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(500)
}
