package com.eorder.app.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.eorder.app.R
import com.eorder.app.viewmodels.MainViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.domain.models.ValidationError
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        setListeners()
        showSessionExpiredMessage()
        init()
    }

    fun setValidationErrors(errors: List<ValidationError>?) {
        textView_activity_main_error_validation_message.text =
            errors?.firstOrNull { it -> it.fieldName == "centerCode" }?.errorMessage
    }

    fun setListeners() {

        val loginButton = findViewById<Button>(R.id.button_signIn)
        loginButton.setOnClickListener { v ->
            startActivity(Intent(this, LoginActivity::class.java))

        }
        button_activity_main_activate_center.setOnClickListener {

            val errors = model.validateModel(editText_activity_main_code_input.text.toString())
            if (errors != null) {
                setValidationErrors(errors)
            } else {
                val intent = Intent(this, CheckEmailActivity::class.java)
                intent.putExtra("centerCode", editText_activity_main_code_input.text.toString())
                startActivity(intent)
            }
        }

        editText_activity_main_code_input.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView_activity_main_error_validation_message.text = ""
            }

        })

    }

    fun showSessionExpiredMessage() {

        if (intent.getStringExtra("session_expired") != null)
            AlertDialogOk(
                this,
                resources.getString(R.string.main_activity_session_expired),
                intent.getStringExtra("session_expired"),
                "OK"
            ) { d, i -> }.show()

    }


    private fun init() {

        model.loadSessionToken(this)
        if (model.isLogged())
            startActivity(Intent(this, LandingActivity::class.java))
    }


}
