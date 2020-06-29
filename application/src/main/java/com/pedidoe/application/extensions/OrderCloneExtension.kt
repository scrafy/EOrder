package com.pedidoe.application.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.models.Order


@RequiresApi(Build.VERSION_CODES.O)
fun Order.clone(): Order {


    val json = Gson.Create().toJson(this)
    return Gson.Create().fromJson(json, this::class.java)
}