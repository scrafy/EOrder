package com.eorder.domain.enumerations

import com.google.gson.annotations.SerializedName

enum class TaxType(var code: Int) {

    @SerializedName("0")
    IVA (0),
    @SerializedName("1")
    IGIC (1)
}