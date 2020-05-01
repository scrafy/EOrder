package com.eorder.app.activities

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recover_password.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class RecoverPasswordActivity : AppCompatActivity(), IShowSnackBarMessage, IManageFormErrors {

    private lateinit var model: RecoverPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)
        model = getViewModel()
        setListeners()
        setObservers()
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {
        textView_activity_recover_password_error_validation_message.text =
            errors?.firstOrNull { it -> it.fieldName == "email" }?.errorMessage
    }

    override fun showMessage(message: String) {
        editText_activity_main_code_input.text.clear()
        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_activity_recover_password_conatiner),
            resources.getString(R.string.close),
            message
        ).show()
    }

    fun setObservers() {

        model.recoverPasswordResult.observe(this, Observer<ServerResponse<Any>> {

            AlertDialogOk(
                this,
                resources.getString(R.string.product_calendar_activity_dialog_email_confirmation),
                resources.getString(R.string.product_calendar_activity_dialog_email_message),
                resources.getString(R.string.ok)
            ) { d, i -> this.onBackPressed() }.show()

        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

    }


    private fun setListeners() {

        button_activity_recover_password_send.setOnClickListener {

            model.recoverPassword(Email(editText_activity_recover_password_mail_input.text.toString()))
        }

        editText_activity_recover_password_mail_input.addTextChangedListener(object :
            TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView_activity_recover_password_error_validation_message.text = ""
            }

        })
    }

}
