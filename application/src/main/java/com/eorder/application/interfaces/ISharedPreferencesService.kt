package com.eorder.application.interfaces

import android.content.Context

interface ISharedPreferencesService {

    fun <T> loadFromSharedPreferences(context: Context?, key: String, klass: Class<T>): T?
    fun <T> writeToSharedPreferences(context: Context?, obj: T?, key: String, klass: Class<T>)
}