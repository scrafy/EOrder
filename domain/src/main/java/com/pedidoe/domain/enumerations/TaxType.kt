package com.pedidoe.domain.enumerations

import com.google.gson.annotations.SerializedName

enum class TaxType(var code: Int) {

    @SerializedName("0")
    NO_DEFINIDO(0),
    @SerializedName("1")
    IVA (1),
    @SerializedName("2")
    IGIC (2),
    @SerializedName("3")
    EXENTO (3)
}