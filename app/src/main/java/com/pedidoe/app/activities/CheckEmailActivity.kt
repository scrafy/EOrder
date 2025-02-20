package com.pedidoe.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pedidoe.app.R
import com.pedidoe.app.com.eorder.app.viewmodels.CheckEmailActivityModel
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.IManageFormErrors
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.AccountCentreCode
import com.pedidoe.domain.models.ValidationError
import kotlinx.android.synthetic.main.activity_check_email.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class CheckEmailActivity : AppCompatActivity(), IShowSnackBarMessage, IManageFormErrors {

    private lateinit var model: CheckEmailActivityModel
    private lateinit var centerCode: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_email)
        model = getViewModel()
        setObservers()
        setListeners()
        centerCode = intent.getStringExtra("centerCode")

    }

    override fun setValidationErrors(errors: List<ValidationError>?) {
        textView_activity_check_email_error_validation_message.text =
            errors?.firstOrNull { it -> it.FieldName == "email" }?.ErrorMessage
    }

    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_activity_check_email_conatiner),
            resources.getString(R.string.close),
            message
        ).show()
    }

    private fun setObservers() {

        model.checkUserEmailResult.observe(this as LifecycleOwner, Observer {

            val result = it.ServerData?.Data!!

            if (result) {
                model.associateAccountToCentreCode(AccountCentreCode(editText_activity_check_email_mail_input.text.toString(), centerCode))
            } else {
                this.finish()
                val intent = Intent(this, CreateAccountActivity::class.java)
                intent.putExtra("email", editText_activity_check_email_mail_input.text.toString())
                intent.putExtra("centerCode", centerCode)
                startActivity(intent)
            }

        })

        model.associateAccountResult.observe(this as LifecycleOwner, Observer {

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("username", it.ServerData?.Data?.username)
            startActivity(Intent(intent))
        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setListeners() {

        button_activity_check_email_send.setOnClickListener {

            model.checkUserEmail(editText_activity_check_email_mail_input.text.toString())
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
