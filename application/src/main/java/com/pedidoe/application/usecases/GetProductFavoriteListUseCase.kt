package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IGetProductFavoriteListUseCase
import com.pedidoe.domain.interfaces.IProductRepository
import com.pedidoe.domain.models.ServerResponse


class GetProductFavoriteListUseCase(
    private val productRepository: IProductRepository
): IGetProductFavoriteListUseCase {


    override fun GetFavoriteProducts(): ServerResponse<List<Int>> {

        return productRepository.getProductsFromFavoriteList()
    }
}