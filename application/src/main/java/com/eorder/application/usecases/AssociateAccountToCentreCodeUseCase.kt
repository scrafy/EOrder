package com.eorder.application.usecases

import com.eorder.application.interfaces.IAssociateAccountToCentreCodeUseCase
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.AccountCentreCode
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.UserProfile


class AssociateAccountToCentreCodeUseCase(
    private val centerRepository: ICenterRepository
) : IAssociateAccountToCentreCodeUseCase
{
    override fun AssociateAccountToCentreCode(data: AccountCentreCode): ServerResponse<UserProfile> {

        return centerRepository.associateAccountToCentreCode(data)
    }

}

