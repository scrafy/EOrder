package com.pedidoe.app.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.pedidoe.app.R
import com.pedidoe.app.viewmodels.ActivateCenterViewModel
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.IManageFormErrors
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.ValidationError
import kotlinx.android.synthetic.main.activity_activate_center.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class ActivateCenterActivity : AppCompatActivity(), IManageFormErrors, IShowSnackBarMessage {

    private lateinit var model: ActivateCenterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate_center)
        model = getViewModel()
        setListeners()
        setObservers()
        /*init()*/
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_activity_center_conatiner),
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun onResume() {

        super.onResume()
        this.findViewById<EditText>(R.id.editText_activity_center_code_input).setText("")
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {

        textView_activity_activate_center_error_validation_message.text =
            errors?.firstOrNull { it -> it.FieldName == "centerCode" }?.ErrorMessage
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setObservers() {

        model.checkCenterActivationCodeResult.observe(this, Observer<ServerResponse<Boolean>> {

            val result = it.ServerData?.Data ?: false

            if (!result) {
                editText_activity_center_code_input.setText("")
                AlertDialogOk(
                    this,
                    resources.getString(R.string.main_activity_dialog_center_title),
                    resources.getString(R.string.main_activity_dialog_center_message),
                    resources.getString(R.string.ok)
                ) { d, i ->



                }.show()

            } else {
                val intent = Intent(this, CheckEmailActivity::class.java)
                intent.putExtra("centerCode", editText_activity_center_code_input.text.toString())
                startActivity(intent)
            }
        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })
    }

    fun setListeners() {

        button_activity_center.setOnClickListener {

            model.checkActivationCenterCode(editText_activity_center_code_input.text.toString())
        }

        editText_activity_center_code_input.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView_activity_activate_center_error_validation_message.text = ""
            }

        })

    }
}
