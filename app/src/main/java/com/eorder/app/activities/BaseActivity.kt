package com.eorder.app.com.eorder.app.activities

import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    abstract fun signOutApp()
    abstract fun checkValidSession()


    override fun onStart() {
        this.checkValidSession()
        super.onStart()
    }
}