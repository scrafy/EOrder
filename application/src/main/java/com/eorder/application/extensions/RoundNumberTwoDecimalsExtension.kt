package com.eorder.application.extensions

import kotlin.math.round

fun Float.round(decimals: Int): Float {

    var multiplier = 1.0F
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}