package com.eorder.application.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

interface IProductsBySellerUseCase {

    fun getProductsBySeller(sellerId: Int) : ServerResponse<List<Product>>
}
