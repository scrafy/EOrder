package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.ChangePassword
import com.pedidoe.domain.models.ServerResponse


interface IChangePasswordUseCase {

    fun changePassword(changePassword: ChangePassword) : ServerResponse<Any>
}