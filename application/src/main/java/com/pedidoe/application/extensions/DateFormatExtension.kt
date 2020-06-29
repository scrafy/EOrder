package com.pedidoe.application.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.convertToString(): String {

    return "${DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(this)}"
}

