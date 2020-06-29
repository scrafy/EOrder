package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.ISellersUseCase
import com.pedidoe.domain.interfaces.ISellerRepository
import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse

class SellersUseCase(
    private val sellerRepository: ISellerRepository
) : ISellersUseCase {

    override fun sellers(): ServerResponse<List<Seller>> {

        return sellerRepository.getSellers()
    }
}