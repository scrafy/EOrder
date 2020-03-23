package com.eorder.app.com.eorder.app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.eorder.app.activities.MainActivity
import com.eorder.infrastructure.interfaces.IJwtTokenService
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity() {

    protected val tokenService: IJwtTokenService = inject<IJwtTokenService>().value


    open fun checkToken() {
        if (!tokenService.isValidToken()) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onStart() {
        this.checkToken()
        super.onStart()
    }
}