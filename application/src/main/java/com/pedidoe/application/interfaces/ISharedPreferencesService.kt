package com.pedidoe.application.interfaces

import android.content.Context
import java.lang.reflect.Type

interface ISharedPreferencesService {

    fun <T> loadFromSharedPreferences(context: Context?, key: String, type: Type): T?
    fun writeToSharedPreferences(context: Context?, obj: Any?, key: String, type: Type)
    fun loadSession(context: Context?, key: String): String?
    fun writeSession(context: Context?, token: String?, key: String)
}