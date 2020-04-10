package com.eorder.application.usecases

import com.eorder.application.interfaces.IProductsBySellerUseCase
import com.eorder.domain.interfaces.IProductRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse


class ProductsBySellerUseCase(
    private val productRepository: IProductRepository
) : IProductsBySellerUseCase {

    override fun getProductsBySeller(centerId:Int, sellerId: Int): ServerResponse<List<Product>> {
        return productRepository.getProductsBySeller(centerId, sellerId)
    }
}