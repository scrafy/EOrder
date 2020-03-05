package com.eorder.app.activities

import android.widget.Toast
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.exceptions.ServerErrorException
import com.eorder.domain.exceptions.ServerErrorUnhadledException
import com.eorder.domain.exceptions.ServerErrorValidationException
import com.eorder.domain.models.ValidationError

abstract class BaseHandleErrorActivity : BaseActivity() {

    protected abstract fun setValidationErrors(errors: List<ValidationError>?)
    protected abstract fun clearEditTextAndFocus()


    protected open fun manageException(ex: Throwable){


        when(ex::class.simpleName){

            "ModelValidationException" -> setValidationErrors((ex as ModelValidationException).validationErrors)
            "ServerErrorValidationException" -> setValidationErrors((ex as ServerErrorValidationException).validationErrors)
            "ServerErrorException"  -> {
                Toast.makeText(this,(ex as ServerErrorException).message, Toast.LENGTH_LONG).show()
                clearEditTextAndFocus()
            }
            "ServerErrorUnhadledException" -> {
                Toast.makeText(this,(ex as ServerErrorUnhadledException).message, Toast.LENGTH_LONG).show()
                clearEditTextAndFocus()
            }
            else -> {
                Toast.makeText(this,(ex as Exception).message, Toast.LENGTH_LONG).show()
                clearEditTextAndFocus()
            }

        }
    }
}
