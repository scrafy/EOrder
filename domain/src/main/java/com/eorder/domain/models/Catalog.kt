package com.eorder.domain.models

import android.graphics.Bitmap
import com.eorder.domain.interfaces.ILoadImageFields

class Catalog(
    val id: Int,
    val name: String,
    val totalProducts: Int,
    val sellerId: Int,
    val sellerName: String,
    override val imageUrl: String? = null,
    override var image: Bitmap? = null

) : ILoadImageFields