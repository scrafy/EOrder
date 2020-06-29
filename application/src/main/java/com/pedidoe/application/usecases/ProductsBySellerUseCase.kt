package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IProductsBySellerUseCase
import com.pedidoe.domain.interfaces.IProductRepository
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse


class ProductsBySellerUseCase(
    private val productRepository: IProductRepository
) : IProductsBySellerUseCase {

    override fun getProductsBySeller(centerId:Int, sellerId: Int): ServerResponse<List<Product>> {
        return productRepository.getProductsBySeller(centerId, sellerId)
    }
}