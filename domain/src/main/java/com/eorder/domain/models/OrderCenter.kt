package com.eorder.domain.models

import android.graphics.Bitmap

data class OrderCenter(

    var centerId: Int? = null,
    var centerName: String? = null,
    var imageUrl: String? = null,
    var image: Bitmap? = null

)