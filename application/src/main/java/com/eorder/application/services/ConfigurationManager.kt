package com.eorder.application.services

import android.content.Context
import com.eorder.domain.interfaces.IConfigurationManager
import java.util.*


class ConfigurationManager(private val context:Context, private val properties: Properties) : IConfigurationManager {


    init{
        properties.load(context.assets.open("config.properties"))
    }

    override fun getProperty(key: String): String {

        return properties.getProperty(key)
    }

}