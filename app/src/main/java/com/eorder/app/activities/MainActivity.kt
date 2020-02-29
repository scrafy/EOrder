package com.eorder.app.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.viewmodels.MainViewModel
import com.eorder.application.models.LoginResponse
import com.eorder.application.models.LoginRequest
import com.eorder.domain.exceptions.ModelValidationException
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.getViewModel



open class MainActivity : AppCompatActivity()  {

    var model: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        setObservers()
        config()

    }

    private fun setObservers(){

        model?.getloginResultsObservable()?.observe(this, Observer<LoginResponse> { it ->

            Log.d("", Gson().toJson(it))
        })

        model?.getErrorObservable()?.observe(this, Observer<Throwable>{ex ->

            if (ex is ModelValidationException)
                Toast.makeText(this,(ex as ModelValidationException).validationErrors.first().errorMessage, Toast.LENGTH_LONG).show()

        })
    }

    private fun config() {

        var button = findViewById(R.id.button) as Button
        var result : LoginResponse


        button.setOnClickListener{ view ->


            var username = findViewById<EditText>(R.id.username)
            var password = findViewById<EditText>(R.id.password)
            var loginRequest = LoginRequest(
                username = username.text.toString(),
                password = password.text.toString()
            )
            model?.login(loginRequest)

        }
    }
}
