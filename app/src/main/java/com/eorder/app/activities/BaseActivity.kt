package com.eorder.app.com.eorder.app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.eorder.app.R
import com.eorder.app.activities.MainActivity
import com.eorder.domain.interfaces.IJwtTokenService
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity() {

    protected val tokenService: IJwtTokenService = inject<IJwtTokenService>().value


    open fun checkToken() {
        if (!tokenService.isValidToken()) {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("session_expired", resources.getString(R.string.main_activity_session_expired_message))
            navigateUpTo(intent)
        }
    }


    override fun onStart() {
        this.checkToken()
        super.onStart()
    }
}