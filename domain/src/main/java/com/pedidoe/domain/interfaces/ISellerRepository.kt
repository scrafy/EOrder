package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse

interface ISellerRepository {

    fun getSellers(): ServerResponse<List<Seller>>
}