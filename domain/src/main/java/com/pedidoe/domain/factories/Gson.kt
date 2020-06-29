package com.pedidoe.domain.factories

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class Gson {

    companion object {

        fun Create(): com.google.gson.Gson {

            return GsonBuilder().registerTypeAdapter(
                LocalDate::class.java,
                LocalDateAdapter().nullSafe()
            )
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter().nullSafe())
                .create()

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {

    override fun write(out: com.google.gson.stream.JsonWriter?, value: LocalDateTime?) {
        out?.value(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").format(value))
    }

    override fun read(input: com.google.gson.stream.JsonReader?): LocalDateTime {

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
        return LocalDateTime.parse(input?.nextString(), formatter)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class LocalDateAdapter : TypeAdapter<LocalDate>() {


    override fun write(out: com.google.gson.stream.JsonWriter?, value: LocalDate?) {
        out?.value(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
    }

    override fun read(input: com.google.gson.stream.JsonReader?): LocalDate {
        return LocalDate.parse(input?.nextString())
    }

}