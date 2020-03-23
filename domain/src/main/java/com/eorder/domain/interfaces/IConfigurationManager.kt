package com.eorder.domain.interfaces

import android.content.Context

interface IConfigurationManager {


    fun getProperty(key:String):String
}