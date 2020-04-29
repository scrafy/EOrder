package com.eorder.domain.models

import android.graphics.Bitmap
import com.eorder.domain.interfaces.ILoadImageFields
import java.io.Serializable

class Seller(

    val id: Int,
    val gln: Long,
    val companyName: String,
    val taxId: String,
    val address: String,
    val city: String,
    val pc: Int,
    val country: String,
    val erp: String? = null,
    val email: String,
    val province: String,
    val sector: String,
    override val imageUrl: String? = null,
    override var image: Bitmap? = null

) :  ILoadImageFields
