package com.eorder.app.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.eorder.app.widgets.*
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class RecoverPasswordActivity : AppCompatActivity(),
    IManageFormErrors,
    IShowSnackBarMessage {

    private lateinit var model: RecoverPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)
        model = getViewModel()
        setObservers()
        setListeners()
    }

    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.relativeLayout_recover_password_activity_root),
            resources.getString(R.string.close),
            message
        ).show()
    }

    fun setObservers() {

        model.getRecoverPasswordObservable().observe(this, Observer<ServerResponse<String>> { it ->

           var dialog = AlertDialogOk(
               this,
               resources.getString(R.string.recover_password_activity_alert_dialog_title),
               resources.getString(R.string.recover_password_activity_alert_dialog_message),
               "OK"
           ) { _, _ ->

               val intent = Intent(this, LoginActivity::class.java)
               startActivity(intent)

           }.show()
        })

        model.getErrorObservable().observe(this, Observer<Throwable>{ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

    }

    fun setListeners() {

        findViewById<Button>(R.id.button_send).setOnClickListener { v ->

            val recoverPasswordRequest = RecoverPassword(findViewById<EditText>(R.id.editText_oldpassword).text.toString(), findViewById<EditText>(R.id.editText_newpassword).text.toString(), findViewById<EditText>(R.id.editText_confirmpassword).text.toString())
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
