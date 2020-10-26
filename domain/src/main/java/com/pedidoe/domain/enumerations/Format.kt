package com.pedidoe.domain.enumerations

import com.google.gson.annotations.SerializedName

enum class Format(var code:Int) {

    @SerializedName("0")
    NO_DEFINIDO(0),
    @SerializedName("1")
    CAJA(1),
    @SerializedName("2")
    UNIDADES(2),
    @SerializedName("3")
    KGS(3),
    @SerializedName("4")
    PACK(4),
    @SerializedName("5")
    BOTELLAS(5),
    @SerializedName("6")
    BRICK (6),
    @SerializedName("7")
    BARRIL(7),
    @SerializedName("8")
    LATA (8),
    @SerializedName("9")
    LITROS (9),
    @SerializedName("10")
    BANDEJA (10),
    @SerializedName("11")
    SACO (11),
    @SerializedName("12")
    CUBO (12)
}
