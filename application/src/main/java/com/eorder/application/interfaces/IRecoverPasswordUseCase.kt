package com.eorder.application.interfaces

import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse

interface IRecoverPasswordUseCase {
    fun recoverPassword(email: Email): ServerResponse<Any>
}
