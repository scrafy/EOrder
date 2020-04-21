package com.eorder.application.interfaces

import com.eorder.domain.models.ServerResponse

interface IActivateCenterUseCase {

    fun activateCenter(code:String, email: String): ServerResponse<Boolean>
}
