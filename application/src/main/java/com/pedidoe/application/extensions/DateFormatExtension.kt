package com.pedidoe.application.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.convertToString(): String {

    return "${DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(this)}"
}


fun Date.convertToLocalDateTime(): LocalDateTime {

    val pattern = "yyyy-MM-dd HH:mm:ss.SSS"
    val simpleDateFormat = SimpleDateFormat(pattern)
    var s = simpleDateFormat.format(this)
    return LocalDateTime.parse(s.replace(" ", "T"))
}

