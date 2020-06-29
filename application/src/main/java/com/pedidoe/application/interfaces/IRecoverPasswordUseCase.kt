package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Email
import com.pedidoe.domain.models.ServerResponse

interface IRecoverPasswordUseCase {
    fun recoverPassword(email: Email): ServerResponse<Any>
}
