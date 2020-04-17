package com.eorder.application.extensions

import com.eorder.domain.models.Order
import com.google.gson.Gson


fun Order.clone() : Order{

    this.products.forEach { it.image = null }
    val json = Gson().toJson(this)
    return Gson().fromJson(json, this::class.java)
}