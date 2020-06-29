package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IAssociateAccountToCentreCodeUseCase
import com.pedidoe.domain.interfaces.ICenterRepository
import com.pedidoe.domain.models.AccountCentreCode
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.UserProfile


class AssociateAccountToCentreCodeUseCase(
    private val centerRepository: ICenterRepository
) : IAssociateAccountToCentreCodeUseCase
{
    override fun AssociateAccountToCentreCode(data: AccountCentreCode): ServerResponse<UserProfile> {

        return centerRepository.associateAccountToCentreCode(data)
    }

}

