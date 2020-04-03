package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetSellerUseCase
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository

class GetSellerUseCase(
    private val unitOfWorkRepository: UnitOfWorkRepository
) : IGetSellerUseCase {

    override fun getSeller(sellerId: Int): ServerResponse<Seller> {

        return unitOfWorkRepository.getSellerRepository().getSeller(sellerId)
    }
}