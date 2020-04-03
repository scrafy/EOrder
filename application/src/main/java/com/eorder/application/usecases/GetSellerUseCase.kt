package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetSellerUseCase
import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse


class GetSellerUseCase(
    private val sellerRepository: ISellerRepository
) : IGetSellerUseCase {

    override fun getSeller(sellerId: Int): ServerResponse<Seller> {

        return sellerRepository.getSeller(sellerId)
    }
}