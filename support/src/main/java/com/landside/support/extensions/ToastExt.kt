package com.landside.support.extensions

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Context.toast(value: String) = toast { value }

fun FragmentActivity.toast(value: String) = toast { value }

fun FragmentActivity.toast(@StringRes resId: Int) =
  popToast(this, resId)

inline fun Context.toast(value: () -> String) =
  popToast(context = this, message = value())

inline fun Fragment.toast(value: () -> String) =
  popToast(activity as Context, value())

fun Context.toast(@StringRes resId: Int) =
  popToast(this, resId)

inline fun Fragment.toast(@StringRes resId: Int) =
  popToast(activity as Context, resId)

inline fun Dialog.toast(value: () -> String) =
  popToast(context, value())

fun popToast(
  context: Context,
  message: String
) {
  val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
  toast.setText(message)
  toast.show()
}

fun popToast(
  context: Context,
  @StringRes resId: Int
) {
  val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
  toast.setText(resId)
  toast.show()
}

