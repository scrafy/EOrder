package com.eorder.app.com.eorder.app.activities

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eorder.app.com.eorder.app.interfaces.IManageFormErrors
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.exceptions.ServerErrorException
import com.eorder.domain.exceptions.ServerErrorUnhadledException
import com.eorder.domain.exceptions.ServerErrorValidationException


abstract class BaseActivity : AppCompatActivity(){


    protected open fun manageException(ex: Throwable){

        when(ex::class.simpleName){

            "ModelValidationException" -> (this as IManageFormErrors).setValidationErrors((ex as ModelValidationException).validationErrors)
            "ServerErrorValidationException" -> (this as IManageFormErrors).setValidationErrors((ex as ServerErrorValidationException).validationErrors)
            "ServerErrorException"  -> {
                Toast.makeText(this,(ex as ServerErrorException).message, Toast.LENGTH_LONG).show()

            }
            "ServerErrorUnhadledException" -> {
                Toast.makeText(this,(ex as ServerErrorUnhadledException).message, Toast.LENGTH_LONG).show()

            }
            else -> {
                Toast.makeText(this,(ex as Exception).message, Toast.LENGTH_LONG).show()

            }

        }
    }
}