package com.eorder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.eorder.R
import models.entities.Entity
import models.entities.User
import services.ValidationService

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
            var user = User(
                username = username.text.toString(),
                password = password.text.toString()
            )
            var service = ValidationService<Entity>()
            var result = service.isModelValid(user)
            println(result)
        }
    }
}
