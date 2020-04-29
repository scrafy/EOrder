package com.eorder.application.gsonadapters

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun write(out: JsonWriter, value: LocalDateTime) {
        out.value(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(value))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun read(input: JsonReader): LocalDateTime {

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        return LocalDateTime.parse(input.nextString(), formatter)
    }
}