package com.eorder.application.usecases

import com.eorder.application.interfaces.IActivateCenterUseCase
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.ServerResponse

class ActivateCenterUseCase(

    private val centerRepository: ICenterRepository
) : IActivateCenterUseCase {

    override fun activateCenter(code: String): ServerResponse<Any> {

        return centerRepository.activateCenter(code)
    }
}