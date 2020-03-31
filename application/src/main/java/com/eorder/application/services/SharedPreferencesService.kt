package com.eorder.application.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.domain.interfaces.IJwtTokenService
import com.google.gson.Gson
import java.lang.reflect.Type


class SharedPreferencesService(

    private val jwtTokenService: IJwtTokenService

) : ISharedPreferencesService {


    override fun <T> loadFromSharedPreferences(
        context: Context?,
        key: String,
        type: Type
    ): T? {

        val userId = jwtTokenService.getClaimFromToken("userId")
        val preferences =
            context?.getSharedPreferences("SHARED_PREFERENCES_${userId}", MODE_PRIVATE)
        val stringJson = preferences?.getString(key, null) ?: return null

        return Gson().fromJson(
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

        val userId = jwtTokenService.getClaimFromToken("userId")
        val preferences =
            context?.getSharedPreferences("SHARED_PREFERENCES_${userId}", MODE_PRIVATE)
        if (obj == null) {
            preferences?.edit()?.putString(key, null)?.commit()
        } else {
            preferences?.edit()?.putString(key, Gson().toJson(obj, type))
                ?.commit()
        }
    }

}