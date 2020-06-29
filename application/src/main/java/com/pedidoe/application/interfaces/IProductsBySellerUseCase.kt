package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse

interface IProductsBySellerUseCase {

    fun getProductsBySeller(centerId:Int, sellerId: Int) : ServerResponse<List<Product>>
}
