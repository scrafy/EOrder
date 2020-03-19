package com.eorder.application.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


fun String.toBitmap(): Bitmap{

    var encodeByte = Base64.decode(this.toByteArray(), Base64.DEFAULT)
    var options = BitmapFactory.Options()
    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size, options)
}

