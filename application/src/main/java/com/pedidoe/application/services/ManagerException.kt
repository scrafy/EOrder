package com.pedidoe.application.services

import com.pedidoe.application.interfaces.ICheckValidSession
import com.pedidoe.application.interfaces.IManagerException
import com.pedidoe.application.interfaces.IManageFormErrors
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.exceptions.ModelValidationException

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