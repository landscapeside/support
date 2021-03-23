package com.landside.support.extensions

import android.view.View

fun Boolean.toVisibility():Int = if (this) View.VISIBLE else View.GONE

fun Boolean.toShown():Int = if (this) View.VISIBLE else View.INVISIBLE