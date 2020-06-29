package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.ServerResponse

interface ICheckUserEmailUseCase {

    fun checkUserEmail(email:String): ServerResponse<Boolean>
}
