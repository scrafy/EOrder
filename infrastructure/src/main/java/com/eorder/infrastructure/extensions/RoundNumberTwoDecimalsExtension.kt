package com.eorder.infrastructure.extensions

import kotlin.math.floor


fun Float.round(decimals: Int): Float {

    /*var multiplier = 1.0F
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier*/
    return floor(this * 100) / 100
}