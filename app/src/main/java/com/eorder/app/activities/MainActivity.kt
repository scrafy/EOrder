package com.eorder.app.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.eorder.app.R
import com.eorder.domain.interfaces.usecases.ILoginUseCase
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
        var result : com.eorder.domain.models.LoginResponse? = null


        button.setOnClickListener{ view ->


            var username = findViewById<EditText>(R.id.username)
            var password = findViewById<EditText>(R.id.password)
            var loginRequest = com.eorder.domain.models.LoginRequest(
                username = username.text.toString(),
                password = password.text.toString()
            )

            result = loginUseCase.login(loginRequest)

        }
    }
}
