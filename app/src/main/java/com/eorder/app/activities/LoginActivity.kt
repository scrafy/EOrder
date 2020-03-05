package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.viewmodels.LoginViewModel
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.domain.models.ValidationError
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginActivity : BaseViewModelActivity() {


    var model: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout. activity_login)
        model = getViewModel()
        setObservers()
        setListeners()
    }


    override fun setObservers(){

        model?.getloginResultsObservable()?.observe(this, Observer<LoginResponse> { it ->

            Log.d("", Gson().toJson(it))
        })

        model?.getErrorObservable()?.observe(this, Observer<Throwable>{ex ->

            manageException(ex)

        })
    }




    override fun setValidationErrors(errors: List<ValidationError>?){

        findViewById<TextView>(R.id.textView_message_error_username).text =
            errors?.firstOrNull{ it -> it.fieldName.equals("username")}?.errorMessage
                ?: null

        findViewById<TextView>(R.id.textView_message_error_password).text =
            errors?.firstOrNull{ it -> it.fieldName.equals("password")}?.errorMessage
                ?: null
    }

    override fun clearEditTextAndFocus(){
        findViewById<EditText>(R.id.editText_username).getText().clear()
        findViewById<EditText>(R.id.editText_password).getText().clear()
        findViewById<EditText>(R.id.editText_username).requestFocus()
    }

    override fun setListeners(){

        findViewById<Button>(R.id.button_signIn).setOnClickListener { view ->

            var loginRequest = LoginRequest(findViewById<EditText>(R.id.editText_username).text.toString(), findViewById<EditText>(R.id.editText_password).text.toString())
            model?.login(loginRequest)
        }

        findViewById<EditText>(R.id.editText_username).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextView>(R.id.textView_message_error_username).setText(null)
            }
        })

        findViewById<EditText>(R.id.editText_password).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findViewById<TextView>(R.id.textView_message_error_password).setText(null)
            }
        })

        findViewById<TextView>(R.id.textView_recoverPassword).setOnClickListener { v ->

            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}
