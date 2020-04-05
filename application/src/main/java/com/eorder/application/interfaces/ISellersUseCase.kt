package com.eorder.application.interfaces

import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse

interface ISellersUseCase {

    fun sellers(): ServerResponse<List<Seller>>

}
