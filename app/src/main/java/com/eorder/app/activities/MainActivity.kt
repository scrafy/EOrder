package com.eorder.app.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.eorder.app.R
import com.eorder.app.com.eorder.app.viewmodels.MainViewModel
import com.eorder.app.dialogs.AlertDialogOk
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        setListeners()
        showSessionExpiredMessage()
        init()
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


    private fun init() {

        model.loadSessionToken(this)
        if (model.isLogged())
            startActivity(Intent(this, LandingActivity::class.java))
    }


}
