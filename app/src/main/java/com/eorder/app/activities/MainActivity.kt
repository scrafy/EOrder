package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.eorder.app.R
import com.eorder.app.dialogs.AlertDialogOk


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
        showSessionExpiredMessage()
    }

    fun setListeners() {

        val loginButton = findViewById<Button>(R.id.button_signIn)
        loginButton.setOnClickListener { v ->
            startActivity(Intent(this, LoginActivity::class.java))

        }

    }

    fun showSessionExpiredMessage() {

        if (intent.getStringExtra("session_expired") != null)
            AlertDialogOk(
                this,
                resources.getString(R.string.main_activity_session_expired),
                intent.getStringExtra("session_expired"),
                "OK"
            ) { d, i -> }.show()

    }

}
