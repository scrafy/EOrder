package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity


 class MainActivity : BaseActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()

    }

    override fun setListeners() {

        val loginButton = findViewById<Button>(R.id.button_signIn)
        loginButton.setOnClickListener { v ->

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
