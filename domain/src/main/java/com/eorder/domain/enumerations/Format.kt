package com.eorder.domain.enumerations

import com.google.gson.annotations.SerializedName

enum class Format(var code:Int) {

    @SerializedName("1")
    Caja(1),
    @SerializedName("2")
    Unidades(2),
    @SerializedName("3")
    Kgs(3),
    @SerializedName("4")
    Pack(4),
    @SerializedName("5")
    Botellas(5)
}
