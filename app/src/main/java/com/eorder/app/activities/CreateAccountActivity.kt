package com.eorder.app.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.viewmodels.CreateAccountViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Account
import com.eorder.domain.models.ValidationError
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
            errors?.firstOrNull { it -> it.fieldName.equals("username") }?.errorMessage


        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_password).error =
            errors?.firstOrNull { it -> it.fieldName.equals("password") }?.errorMessage


        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_confirm_password).error =
            errors?.firstOrNull { it -> it.fieldName.equals("confirmPassword") }?.errorMessage

        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_phone).error =
            errors?.firstOrNull { it -> it.fieldName.equals("phone") }?.errorMessage

        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_email).error =
            errors?.firstOrNull { it -> it.fieldName.equals("email") }?.errorMessage

        findViewById<TextInputLayout>(R.id.textInputLayout_create_profile_activity_centerCode).error =
            errors?.firstOrNull { it -> it.fieldName.equals("centerCode") }?.errorMessage

    }

    override fun showMessage(message: String) {
        clearEditTextAndFocus()
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

        model.createAccountResult.observe(this as LifecycleOwner, Observer<Any> {

            AlertDialogOk(this, "Account", "The account has been created correctly", "OK") { d, i ->
                this.finish()
                startActivity(Intent(this, LoginActivity::class.java))
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
