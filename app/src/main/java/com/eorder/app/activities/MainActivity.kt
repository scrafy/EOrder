package com.eorder.app.activities


import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.eorder.app.R
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.application.models.LoginRequest
import org.koin.android.ext.android.inject



open class MainActivity : AppCompatActivity()  {

    private val loginUseCase: ILoginUseCase by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        config()
    }

    private fun config() {

        var button = findViewById(R.id.button) as Button



        button.setOnClickListener{ view ->

            var t = getSystemService(Context.CONNECTIVITY_SERVICE)

            //var n =  t.activeNetworkInfo.isConnected


            var username = findViewById<EditText>(R.id.username)
            var password = findViewById<EditText>(R.id.password)
            var loginRequest = LoginRequest(
                username = username.text.toString(),
                password = password.text.toString()
            )

            var result = loginUseCase.login(loginRequest)
            println(result)
        }
    }
}
