package com.eorder.application.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.factories.Gson
import com.eorder.domain.models.Order


@RequiresApi(Build.VERSION_CODES.O)
fun Order.clone(): Order {

    this.products.forEach { it.image = null }
    val json = Gson.Create().toJson(this)
    return Gson.Create().fromJson(json, this::class.java)
}