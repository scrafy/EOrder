package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Email
import com.pedidoe.domain.models.ServerResponse

interface IExistsUserEmailUseCase {

    fun checkExistUserEmail(email: Email) :  ServerResponse<Boolean>
}