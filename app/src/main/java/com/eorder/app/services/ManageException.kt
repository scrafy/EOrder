package com.eorder.app.services


import android.content.Context
import com.eorder.app.interfaces.IManageException
import com.eorder.app.interfaces.IManageFormErrors
import com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.exceptions.ServerErrorValidationException

class ManageException : IManageException {

    override fun manageException(context: Context, ex: Throwable) {

        when(ex::class.simpleName){

            "ModelValidationException" -> (context as IManageFormErrors).setValidationErrors((ex as ModelValidationException).validationErrors)
            "ServerErrorValidationException" -> (context as IManageFormErrors).setValidationErrors((ex as ServerErrorValidationException).validationErrors)
            "ServerErrorException"  -> {
                (context as IShowSnackBarMessage).showMessage(ex.message ?: "")

            }
            "ServerErrorUnhadledException" -> {
                (context as IShowSnackBarMessage).showMessage(ex.message ?: "")

            }
            "InvalidJwtTokenException" ->  {

                (context as IShowSnackBarMessage).showMessage(ex.message ?: "")
            }
            else -> {

                (context as IShowSnackBarMessage).showMessage(ex.message ?: "")
            }

        }
    }

}