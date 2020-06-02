package com.eorder.application.usecases

import com.eorder.application.interfaces.IAssociateAccountToCentreCodeUseCase
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.AccountCentreCode
import com.eorder.domain.models.ServerResponse


class AssociateAccountToCentreCodeUseCase(
    private val centerRepository: ICenterRepository
) : IAssociateAccountToCentreCodeUseCase
{
    override fun AssociateAccountToCentreCode(data: AccountCentreCode): ServerResponse<Any> {

        return centerRepository.associateAccountToCentreCode(data)
    }

}

