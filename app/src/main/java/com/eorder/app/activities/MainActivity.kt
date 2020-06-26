package com.eorder.app.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.viewmodels.MainViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError
import kotlinx.android.synthetic.main.activity_main.*
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

        button_activity_main_login.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
        }

        button_activity_main_create_account.setOnClickListener {

            startActivity(Intent(this, ActivateCenterActivity::class.java))
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
