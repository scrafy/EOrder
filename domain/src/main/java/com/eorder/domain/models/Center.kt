package com.eorder.domain.models

import android.graphics.Bitmap
import com.eorder.domain.interfaces.ILoadImageFields
import java.io.Serializable

class Center(

    val id: Int,
    val center_name: String,
    val address: String,
    val city: String,
    val pc: Int,
    val country: String,
    val email: String,
    val province: String,
    val sector: String,
    override val imageUrl: String? = null,
    override var image: Bitmap? = null

    ): Serializable, ILoadImageFields

