package com.eorder.application.usecases

import com.eorder.application.interfaces.ISellerUseCase
import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse


class SellerUseCase(
    private val sellerRepository: ISellerRepository
) : ISellerUseCase {

    override fun getSeller(sellerId: Int): ServerResponse<Seller> {

        return sellerRepository.getSeller(sellerId)
    }
}