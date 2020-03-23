package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : BaseActivity() {

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        setListeners()

    }

    fun setListeners() {

        val loginButton = findViewById<Button>(R.id.button_signIn)
        loginButton.setOnClickListener { v ->

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    override fun checkToken() {
        if (tokenService.isValidToken()) {
            startActivity(Intent(this, LandingActivity::class.java))
        }

    }

}
