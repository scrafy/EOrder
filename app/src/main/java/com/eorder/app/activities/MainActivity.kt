package com.eorder.app.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.viewmodels.MainViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.ValidationError
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), IShowSnackBarMessage, IManageFormErrors {

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        setListeners()
        setObservers()
        showSessionExpiredMessage()
        init()
    }

    override fun clearEditTextAndFocus() {
        button_activity_main_code_input.text.clear()
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {
        textView_activity_main_error_validation_message.text =
            errors?.firstOrNull { it -> it.fieldName == "centerCode" }?.errorMessage
    }

    override fun showMessage(message: String) {
        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_activity_main_conatiner),
            resources.getString(R.string.close),
            message
        ).show()
    }

    private fun setObservers() {

        model.activateCenterResult.observe(this as LifecycleOwner, Observer<Any> {

            AlertDialogOk(this, "Center activated", "The center has been activated correctly", "OK") { d, i ->}.show()
        })
    }

    fun setListeners() {

        val loginButton = findViewById<Button>(R.id.button_signIn)
        loginButton.setOnClickListener { v ->
            startActivity(Intent(this, LoginActivity::class.java))

        }
        button_activity_main_activate_center.setOnClickListener {

            model.activateCenter(button_activity_main_code_input.text.toString())
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
