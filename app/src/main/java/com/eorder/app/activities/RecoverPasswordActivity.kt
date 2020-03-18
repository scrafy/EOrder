package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.interfaces.IManageFormErrors
import com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.eorder.application.models.RecoverPasswordRequest
import com.eorder.domain.models.ValidationError
import com.eorder.infrastructure.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RecoverPasswordActivity : AppCompatActivity(), IManageFormErrors, IShowSnackBarMessage {

    private lateinit var model: RecoverPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)
        model = getViewModel()
        setObservers()
        setListeners()
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setObservers() {

        model.getRecoverPasswordObservable().observe(this, Observer<ServerResponse<String>> { it ->

           var dialog = AlertDialogOk(R.layout.alert_dialog_ok,this, "Password Recovery", "The password has been changed correctly","OK") { _, _->

               val intent = Intent(this, LoginActivity::class.java)
               startActivity(intent)

           }.show()
        })

        model.getErrorObservable().observe(this, Observer<Throwable>{ex ->

            model.manageExceptionService.manageException(this, ex)

        })

    }

    fun setListeners() {

        findViewById<Button>(R.id.button_send).setOnClickListener { v ->

            val recoverPasswordRequest = RecoverPasswordRequest(findViewById<EditText>(R.id.editText_oldpassword).text.toString(), findViewById<EditText>(R.id.editText_newpassword).text.toString(), findViewById<EditText>(R.id.editText_confirmpassword).text.toString())
            model.recoverPassword(recoverPasswordRequest)
        }

        findViewById<EditText>(R.id.editText_oldpassword).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextView>(R.id.textView_message_error_oldpassword).setText(null)
            }
        })

        findViewById<EditText>(R.id.editText_newpassword).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextView>(R.id.textView_message_error_newpassword).setText(null)
            }
        })

        findViewById<EditText>(R.id.editText_confirmpassword).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextView>(R.id.textView_message_error_confirmpassword).setText(null)
            }
        })
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {

        findViewById<TextView>(R.id.textView_message_error_oldpassword).text =
            errors?.firstOrNull{ it -> it.fieldName.equals("oldPassword")}?.errorMessage


        findViewById<TextView>(R.id.textView_message_error_newpassword).text =
            errors?.firstOrNull{ it -> it.fieldName.equals("newPassword")}?.errorMessage


        findViewById<TextView>(R.id.textView_message_error_confirmpassword).text =
            errors?.firstOrNull{ it -> it.fieldName.equals("confirmPassword")}?.errorMessage

    }

    override fun clearEditTextAndFocus() {

        findViewById<EditText>(R.id.editText_oldpassword).getText().clear()
        findViewById<EditText>(R.id.editText_newpassword).getText().clear()
        findViewById<EditText>(R.id.editText_confirmpassword).getText().clear()
        findViewById<EditText>(R.id.editText_oldpassword).requestFocus()
    }
}
