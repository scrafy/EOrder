package com.pedidoe.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pedidoe.app.R
import com.pedidoe.app.com.eorder.app.viewmodels.CreateAccountViewModel
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.IManageFormErrors
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.Account
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.UserProfile
import com.pedidoe.domain.models.ValidationError
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_create_profile.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CreateAccountActivity : AppCompatActivity(), IShowSnackBarMessage, IManageFormErrors {

    private lateinit var model: CreateAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        model = getViewModel()
        setListeners()
        setObservers()
        init()
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {

        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_username).error =
            errors?.firstOrNull { it -> it.FieldName.equals("Username") }?.ErrorMessage


        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_password).error =
            errors?.firstOrNull { it -> it.FieldName.equals("Password") }?.ErrorMessage


        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_confirm_password).error =
            errors?.firstOrNull { it -> it.FieldName.equals("ConfirmPassword") }?.ErrorMessage

        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_phone).error =
            errors?.firstOrNull { it -> it.FieldName.equals("Phone") }?.ErrorMessage

        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_email).error =
            errors?.firstOrNull { it -> it.FieldName.equals("Email") }?.ErrorMessage

        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_centerCode).error =
            errors?.firstOrNull { it -> it.FieldName.equals("CentreCode") }?.ErrorMessage

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_create_profile_activity_root),
            resources.getString(R.string.close),
            message
        ).show()
    }

    private fun clearEditTextAndFocus() {

        findViewById<EditText>(R.id.editText_create_profile_activity_username).text.clear()
        findViewById<EditText>(R.id.editText_create_profile_activity_password).text.clear()
        findViewById<EditText>(R.id.editText_create_profile_activity_confirm_password).text.clear()
        findViewById<EditText>(R.id.editText_create_profile_activity_phone).text.clear()
        findViewById<EditText>(R.id.editText_create_profile_activity_username).requestFocus()
    }

    private fun setObservers() {

        model.createAccountResult.observe(this as LifecycleOwner, Observer<ServerResponse<UserProfile>> {

            AlertDialogOk(this, resources.getString(R.string.create_account_activity_alert_title), resources.getString(R.string.create_account_activity_alert_message), resources.getString(R.string.ok)) { d, i ->
                this.finish()
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("username", it.ServerData?.Data?.username)
                startActivity(Intent(intent))
                clearEditTextAndFocus()
            }.show()
        })


        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })
    }

    private fun setListeners() {

        button_create_profile_activity_button.setOnClickListener {

            val account = Account(

                editText_create_profile_activity_username.text.toString(),
                editText_create_profile_activity_password.text.toString(),
                editText_create_profile_activity_confirm_password.text.toString(),
                editText_create_profile_activity_phone.text.toString(),
                editText_create_profile_activity_email.text.toString(),
                editText_create_profile_activity_centerCode.text.toString()
            )

            model.createAccount(account)

        }

        editText_create_profile_activity_confirm_password.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_confirm_password).error =
                    null
            }
        })

        editText_create_profile_activity_password.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_password).error =
                    null
            }
        })

        editText_create_profile_activity_phone.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_phone).error =
                    null
            }
        })

        editText_create_profile_activity_username.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_username).error =
                    null
            }
        })
    }

    private fun init() {

        if (intent.getStringExtra("email") != null) {
            editText_create_profile_activity_email.setText(intent.getStringExtra("email").toString())
        } else {
            editText_create_profile_activity_email.inputType = InputType.TYPE_NULL
        }
        if (intent.getStringExtra("centerCode") != null) {
            editText_create_profile_activity_centerCode.setText(intent.getStringExtra("centerCode").toString())
        } else {
            editText_create_profile_activity_centerCode.inputType = InputType.TYPE_NULL
        }


    }
}
