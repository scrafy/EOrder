package com.eorder.application.services

import com.eorder.application.interfaces.ICheckValidSession
import com.eorder.application.interfaces.IManagerException
import com.eorder.application.interfaces.IManageFormErrors
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.exceptions.ModelValidationException

class ManagerException : IManagerException {

    override fun manageException(context: Any, ex: Throwable) {

        when(ex::class.simpleName){

            "ModelValidationException" -> (context as IManageFormErrors).setValidationErrors((ex as ModelValidationException).validationErrors)

            "UnauthorizedException" -> {
                (context as ICheckValidSession).checkValidSession()
            }
            else -> {

                (context as IShowSnackBarMessage).showMessage(ex.message ?: "An error has happenned")
            }

        }
    }

}