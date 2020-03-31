package com.eorder.application.services


import com.eorder.application.interfaces.IManagerException
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.exceptions.ServerErrorValidationException

class ManagerException : IManagerException {

    override fun manageException(context: Any, ex: Throwable) {

        when(ex::class.simpleName){

            "ModelValidationException" -> (context as IManageFormErrors).setValidationErrors((ex as ModelValidationException).validationErrors)
            "ServerErrorValidationException" -> (context as IManageFormErrors).setValidationErrors((ex as ServerErrorValidationException).validationErrors)
            "ServerErrorException"  -> {
                (context as IShowSnackBarMessage).showMessage(ex.message ?: "An error has hapenned")

            }
            "ServerErrorUnhadledException" -> {
                (context as IShowSnackBarMessage).showMessage(ex.message ?: "An error has hapenned")

            }
            "InvalidJwtTokenException" ->  {

                (context as IShowSnackBarMessage).showMessage(ex.message ?: "An error has hapenned")
            }
            else -> {

                (context as IShowSnackBarMessage).showMessage(ex.message ?: "An error has hapenned")
            }

        }
    }

}