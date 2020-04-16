package com.eorder.application.extensions

import android.content.res.Resources
import kotlin.math.roundToInt

fun Float.convertDpToPixel(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return px.roundToInt()
}