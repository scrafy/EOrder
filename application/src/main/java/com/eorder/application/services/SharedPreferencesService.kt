package com.eorder.application.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.gsonadapters.LocalDateAdapter
import com.eorder.application.gsonadapters.LocalDateTimeAdapter
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.domain.interfaces.IJwtTokenService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
class SharedPreferencesService(

    private val jwtTokenService: IJwtTokenService

) : ISharedPreferencesService {

    private var gson: Gson =
        GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateAdapter().nullSafe())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter().nullSafe())
            .create()

    override fun loadSession(context: Context?, key: String): String? {

        val preferences =
            context?.getSharedPreferences("SESSION", MODE_PRIVATE)
        val stringJson = preferences?.getString(key, null) ?: return null

        return gson.fromJson(
            stringJson,
            String::class.java
        )
    }

    override fun writeSession(
        context: Context?,
        token: String?,
        key: String
    ) {
        val preferences =
            context?.getSharedPreferences("SESSION", MODE_PRIVATE)

        if (preferences != null && preferences.contains(key) && token == null) {

            preferences.edit().remove(key).commit()
            return
        }
        preferences?.edit()?.putString(key, gson.toJson(token, String::class.java))
            ?.commit()
    }

    override fun <T> loadFromSharedPreferences(
        context: Context?,
        key: String,
        type: Type
    ): T? {

        val userId = jwtTokenService.getClaimFromToken("userId") ?: return null

        val preferences =
            context?.getSharedPreferences("STORE_${userId}", MODE_PRIVATE)
        val stringJson = preferences?.getString(key, null) ?: return null

        return gson.fromJson(
            stringJson,
            type
        )
    }

    override fun writeToSharedPreferences(
        context: Context?,
        obj: Any?,
        key: String,
        type: Type
    ) {

        val userId = jwtTokenService.getClaimFromToken("userId")!!

        val preferences =
            context?.getSharedPreferences("STORE_${userId}", MODE_PRIVATE)

        if (preferences != null && preferences.contains(key) && obj == null) {

            preferences.edit().remove(key).commit()
            return
        }

        preferences?.edit()?.putString(key, gson.toJson(obj, type))
            ?.commit()

    }
}