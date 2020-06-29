package com.pedidoe.domain.enumerations

import com.google.gson.annotations.SerializedName

enum class Sector(var code:Int) {

    @SerializedName("0")
    NO_DEFINIDO(0),
    @SerializedName("1")
    HORECA(1),
    @SerializedName("2")
    FARMACEUTICO(2),
    @SerializedName("3")
    PANADERIAS(3),

}
