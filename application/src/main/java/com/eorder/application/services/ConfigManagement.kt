package com.eorder.application.services

import android.app.Application
import android.content.Context
import android.net.Uri
import com.eorder.application.R
import java.io.File
import java.io.FileReader
import java.nio.file.Path


class ConfigManagement {

    companion object ConfigManagement {

        fun loadConfig() : String?{

           return  ConfigManagement::class.java.getResource("/xml/config.xml")?.readText()
        }
    }
}