package com.eorder.app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.viewmodels.LoginViewModel
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.exceptions.ServerErrorException
import com.eorder.domain.exceptions.ServerErrorUnhadledException
import com.eorder.domain.exceptions.ServerErrorValidationException
import com.eorder.domain.models.ValidationError
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.Exception

class LoginActivity : BaseActivity() {

    var model: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        model = getViewModel()
        setObservers()
        setListeners()
    }


    private fun setObservers(){

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

    private fun setListeners(){

        findViewById<Button>(R.id.button_signIn).setOnClickListener({view ->

            var loginRequest = LoginRequest(findViewById<EditText>(R.id.editText_username).text.toString(), findViewById<EditText>(R.id.editText_password).text.toString())
            model?.login(loginRequest)
        })

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
    }
}
