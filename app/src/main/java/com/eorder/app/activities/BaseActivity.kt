package com.eorder.app.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    abstract fun checkValidSession()


    override fun onStart() {
        super.onStart()
        this.checkValidSession()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}