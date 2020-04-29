package com.eorder.infrastructure.extensions

import kotlin.math.floor


fun Float.round(decimals: Int): Float {

    return floor(this * 100) / 100
}