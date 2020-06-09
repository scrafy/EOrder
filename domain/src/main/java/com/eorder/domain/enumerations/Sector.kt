package com.eorder.domain.enumerations

import com.google.gson.annotations.SerializedName

enum class Sector(var code:Int) {

    @SerializedName("0")
    Horeca(0),
    @SerializedName("1")
    Farmaceutico(1),
    @SerializedName("2")
    Panaderias(2),

}
