package com.eorder.application.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.eorder.application.interfaces.ISharedPreferencesService
import com.google.gson.Gson
import kotlin.reflect.KClass


class SharedPreferencesService : ISharedPreferencesService {


    override fun <T> loadFromSharedPreferences(
        context: Context?,
        key: String,
        klass: Class<T>
    ): T? {

        val preferences =
            context?.getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE)
        val stringJson = preferences?.getString(key, null) ?: return null

        return Gson().fromJson<T>(
            stringJson,
            klass
        )
    }

    override fun <T> writeToSharedPreferences(
        context: Context?,
        obj: T?,
        key: String,
        klass: Class<T>
    ) {

        val preferences =
            context?.getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE)
        if (obj == null) {
            preferences?.edit()?.putString(key, null)?.commit()
        } else {
            preferences?.edit()?.putString(key, Gson().toJson(obj, klass))
                ?.commit()
        }
    }

}