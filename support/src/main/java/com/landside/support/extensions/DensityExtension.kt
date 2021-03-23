package com.landside.support.extensions

import android.content.res.Resources

private val metric = Resources.getSystem()
    .displayMetrics

fun Int.byDip(): Float = toFloat() * metric.density
fun Int.bySp(): Float = toFloat() * metric.density
fun Int.toDip(): Float = toFloat() / metric.density

