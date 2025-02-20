package com.pedidoe.application.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.ISharedPreferencesService
import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.IJwtTokenService
import java.lang.reflect.Type



@RequiresApi(Build.VERSION_CODES.O)
class SharedPreferencesService(

    private val jwtTokenService: IJwtTokenService

) : ISharedPreferencesService {

    private var gson: com.google.gson.Gson = Gson.Create()


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

        val userId = jwtTokenService.getClaimFromToken("userId") ?: return


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