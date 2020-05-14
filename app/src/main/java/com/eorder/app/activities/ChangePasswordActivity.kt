package com.eorder.app.activities


import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.ChangePasswordViewModel
import com.eorder.app.widgets.*
import com.eorder.domain.models.ChangePassword
import com.eorder.domain.models.Product
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_product_calendar.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class ChangePasswordActivity : BaseFloatingButtonActivity(),
    IManageFormErrors,
    IShowSnackBarMessage {

    private lateinit var model: ChangePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        model = getViewModel()
        setObservers()
        setListeners()
    }

    override fun showMessage(message: String) {
        clearEditTextAndFocus()
        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_change_password_activity_root),
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun getProductsFromShop(): List<Product> {
       return model.getProductsFromShop()
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    override fun onStart() {
        super.onStart()
        this.setSupportActionBar(toolbar as Toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        this.onBackPressed()
        return true
    }


    fun setObservers() {

        model.changePasswordResult.observe(this, Observer<ServerResponse<Any>> { it ->

            var dialog = AlertDialogOk(
                this,
                resources.getString(R.string.change_password),
                resources.getString(R.string.change_password_activity_alert_dialog_message),
                "OK"
            ) { _, _ ->

                this.onBackPressed()
            }.show()

        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

    }

    private fun setListeners() {

        findViewById<Button>(R.id.button_change_password_activity_button).setOnClickListener { v ->

            val changePasswordRequest = ChangePassword(
                findViewById<EditText>(R.id.editText_change_password_activity_old_password).text.toString(),
                findViewById<EditText>(R.id.editText_change_password_activity_new_password).text.toString(),
                findViewById<EditText>(R.id.editText_change_password_activity_confirm_password).text.toString()
            )
            model.changePassword(changePasswordRequest)
        }

        findViewById<EditText>(R.id.editText_change_password_activity_old_password).addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    findViewById<TextInputLayout>(R.id.textInputLayout_change_password_activity_oldpassword).error =
                        null
                }
            })

        findViewById<EditText>(R.id.editText_change_password_activity_new_password).addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    findViewById<TextInputLayout>(R.id.textInputLayout_change_password_activity_newpassword).error =
                        null
                }
            })

        findViewById<EditText>(R.id.editText_change_password_activity_confirm_password).addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    findViewById<TextInputLayout>(R.id.textInputLayout_change_password_activity_confirm_password).error =
                        null
                }
            })
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {

        findViewById<TextInputLayout>(R.id.textInputLayout_change_password_activity_oldpassword).error =
            errors?.firstOrNull { it -> it.FieldName.equals("oldPassword") }?.ErrorMessage


        findViewById<TextInputLayout>(R.id.textInputLayout_change_password_activity_newpassword).error =
            errors?.firstOrNull { it -> it.FieldName.equals("newPassword") }?.ErrorMessage


        findViewById<TextInputLayout>(R.id.textInputLayout_change_password_activity_confirm_password).error =
            errors?.firstOrNull { it -> it.FieldName.equals("confirmPassword") }?.ErrorMessage

    }

    private fun clearEditTextAndFocus() {

        findViewById<EditText>(R.id.editText_change_password_activity_confirm_password).text.clear()
        findViewById<EditText>(R.id.editText_change_password_activity_new_password).text.clear()
        findViewById<EditText>(R.id.editText_change_password_activity_old_password).text.clear()
        findViewById<EditText>(R.id.editText_change_password_activity_old_password).requestFocus()
    }
}
