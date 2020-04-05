package com.eorder.application.usecases

import com.eorder.application.interfaces.ISellersUseCase
import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse

class SellersUseCase(
    private val sellerRepository: ISellerRepository
) : ISellersUseCase {

    override fun sellers(): ServerResponse<List<Seller>> {

        return sellerRepository.getSellers()
    }
}