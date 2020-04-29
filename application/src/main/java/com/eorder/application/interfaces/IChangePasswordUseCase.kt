package com.eorder.application.interfaces

import com.eorder.domain.models.ChangePassword
import com.eorder.domain.models.ServerResponse


interface IChangePasswordUseCase {

    fun changePassword(recoverPassword: ChangePassword) : ServerResponse<Any>
}