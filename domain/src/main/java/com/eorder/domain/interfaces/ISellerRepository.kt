package com.eorder.domain.interfaces

import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse

interface ISellerRepository {

    fun getSellers(): ServerResponse<List<Seller>>
}