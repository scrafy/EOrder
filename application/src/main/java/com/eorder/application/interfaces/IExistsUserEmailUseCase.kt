package com.eorder.application.interfaces

import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse

interface IExistsUserEmailUseCase {

    fun checkExistUserEmail(email: Email) :  ServerResponse<Boolean>
}