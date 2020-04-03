package com.eorder.application.interfaces

import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse

interface IGetSellerUseCase {

    fun getSeller( sellerId: Int ): ServerResponse<Seller>

}
