package com.pedidoe.app.activities

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pedidoe.app.R
import com.pedidoe.app.com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.IManageFormErrors
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.Email
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.ValidationError
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
            errors?.firstOrNull { it -> it.FieldName == "email" }?.ErrorMessage
    }

    override fun showMessage(message: String) {

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
