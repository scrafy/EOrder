package com.eorder.application.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.gsonadapters.LocalDateAdapter
import com.eorder.domain.models.Order
import com.google.gson.GsonBuilder
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
fun Order.clone() : Order{

    this.products.forEach { it.image = null }
    val json = GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateAdapter().nullSafe()).create().toJson(this)
    return GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateAdapter().nullSafe()).create().fromJson(json, this::class.java)
}