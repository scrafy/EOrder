package com.eorder.application.factories

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.gsonadapters.LocalDateAdapter
import com.eorder.application.gsonadapters.LocalDateTimeAdapter
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime

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