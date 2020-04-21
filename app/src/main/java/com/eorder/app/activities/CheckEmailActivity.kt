package com.eorder.app.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.viewmodels.CheckEmailActivityModel
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.ValidationError
import kotlinx.android.synthetic.main.activity_check_email.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel




class CheckEmailActivity : AppCompatActivity(), IShowSnackBarMessage, IManageFormErrors {

    private lateinit var model: CheckEmailActivityModel
    private lateinit var centerCode:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_email)
        model = getViewModel()
        setObservers()
        setListeners()
        centerCode = intent.getStringExtra("centerCode")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {
        textView_activity_check_email_error_validation_message.text =
            errors?.firstOrNull { it -> it.fieldName == "email" }?.errorMessage
    }

    override fun showMessage(message: String) {
        editText_activity_main_code_input.text.clear()
        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_activity_check_email_conatiner),
            resources.getString(R.string.close),
            message
        ).show()
    }

    private fun setObservers() {

        model.activateCenterResult.observe(this as LifecycleOwner, Observer {

            val result = it.serverData?.data!!

            if (result) {
                this.finish()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                this.finish()
                val intent = Intent(this, CreateProfileActivity::class.java)
                intent.putExtra("email",editText_activity_check_email_mail_input.text.toString())
                intent.putExtra("centerCode", centerCode)
                startActivity(intent)
            }

        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

    }

    fun setListeners() {

        button_activity_check_email_send.setOnClickListener {

            model.checkUserEmail(intent.getStringExtra("centerCode"), editText_activity_check_email_mail_input.text.toString())
        }

        editText_activity_check_email_mail_input.addTextChangedListener(object :
            TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView_activity_check_email_error_validation_message.text = ""
            }

        })
    }
}
