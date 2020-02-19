package com.eorder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.eorder.R
import models.LoginRequest
import usecases.LoguinUseCase

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        config()
    }

    private fun config() {

        var button = findViewById(R.id.button) as Button



        button.setOnClickListener{ view ->


            var username = findViewById<EditText>(R.id.username)
            var password = findViewById<EditText>(R.id.password)
            var loginRequest = LoginRequest(
                username = username.text.toString(),
                password = password.text.toString()
            )
            var loginUseCase = LoguinUseCase()
            var result = loginUseCase.login(loginRequest)
            println(result)
        }
    }
}
