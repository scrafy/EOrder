package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse

interface ISellerUseCase {

    fun getSeller( sellerId: Int ): ServerResponse<Seller>

}
