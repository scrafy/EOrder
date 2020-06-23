package com.eorder.app.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import com.eorder.application.interfaces.ICheckValidSession


abstract class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        ( this as ICheckValidSession).checkValidSession()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}