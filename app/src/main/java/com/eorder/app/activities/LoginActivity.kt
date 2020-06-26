package com.eorder.app.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.viewmodels.LoginViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Login
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class LoginActivity : AppCompatActivity(), IManageFormErrors,
    IShowSnackBarMessage {


    private lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        model = getViewModel()
        setObservers()
        setListeners()
        init()
    }

    fun setObservers() {

        model.getloginResultsObservable().observe(this, Observer<ServerResponse<String>> {

            if (!model.userHasCenters()) {

                AlertDialogOk(
                    this,
                    resources.getString(R.string.login_activity_digalog_title),
                    resources.getString(R.string.login_activity_digalog_message),
                    resources.getString(R.string.ok)
                ) { d, i -> clearEditTextAndFocus() }.show()
            } else {
                model.loadShopForSharedPreferencesOrder(this)
                val intent = Intent(this, LandingActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })
    }


    override fun setValidationErrors(errors: List<ValidationError>?) {

        findViewById<TextInputLayout>(R.id.textInputLayout_login_activity_username).error =
            errors?.firstOrNull { it -> it.FieldName == "Username" }?.ErrorMessage



        findViewById<TextInputLayout>(R.id.textInputLayout_login_activity_password).error =
            errors?.firstOrNull { it -> it.FieldName == "Password" }?.ErrorMessage

    }

    override fun showMessage(message: String) {
        clearEditTextAndFocus()
        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_login_activity_root),
            resources.getString(R.string.close),
            message
        ).show()
    }

    private fun clearEditTextAndFocus() {

        findViewById<EditText>(R.id.editText_username).text.clear()
        findViewById<EditText>(R.id.editText_password).text.clear()
        findViewById<EditText>(R.id.editText_username).requestFocus()
    }

    fun setListeners() {

        findViewById<Button>(R.id.button_signIn).setOnClickListener {

            val loginRequest = Login(
                findViewById<EditText>(R.id.editText_username).text.toString(),
                findViewById<EditText>(R.id.editText_password).text.toString()
            )
            model.login(loginRequest)
        }

        findViewById<EditText>(R.id.editText_username).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextInputLayout>(R.id.textInputLayout_login_activity_username).error =
                    null
            }
        })

        findViewById<EditText>(R.id.editText_password).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextInputLayout>(R.id.textInputLayout_login_activity_password).error =
                    null
            }
        })

        findViewById<TextView>(R.id.textView_recoverPassword).setOnClickListener {

            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {

        if ( intent.getStringExtra("username") != null ){
            editText_username.setText(intent.getStringExtra("username"))
            editText_password.requestFocus()
        }else{
            editText_username.requestFocus()
        }

    }

}
