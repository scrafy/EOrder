package com.eorder.domain.enumerations

import com.google.gson.annotations.SerializedName

enum class Currency(var code:Int) {

    @SerializedName("1")
    EUR(1)
}
