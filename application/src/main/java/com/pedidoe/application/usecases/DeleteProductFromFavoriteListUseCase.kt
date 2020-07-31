package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IDeleteProductFromFavoriteListUseCase
import com.pedidoe.domain.interfaces.IProductRepository
import com.pedidoe.domain.models.ServerResponse


class DeleteProductFromFavoriteListUseCase(
    private val productRepository: IProductRepository
): IDeleteProductFromFavoriteListUseCase {

    override fun DeleteProductFromFavorite(productId: Int): ServerResponse<String> {

        return productRepository.deleteProductFromFavoriteList(productId)
    }
}