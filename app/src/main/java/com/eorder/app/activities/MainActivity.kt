package com.eorder.app.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.eorder.app.R




open class MainActivity : AppCompatActivity()  {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {

        val loginButton = findViewById<Button>(R.id.button_signIn)
        loginButton.setOnClickListener({v ->

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

    }
}
