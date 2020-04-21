package com.eorder.application.interfaces

import com.eorder.domain.models.ServerResponse

interface ICheckUserEmailUseCase {

    fun checkUserEmail(email:String): ServerResponse<Boolean>
}
