package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IAddProductToFavoriteListUseCase
import com.pedidoe.domain.interfaces.IProductRepository
import com.pedidoe.domain.models.ServerResponse


class AddProductToFavoriteListUseCase(
    private val productRepository: IProductRepository
): IAddProductToFavoriteListUseCase {


    override fun AddProductToFavorite(productId: Int): ServerResponse<String> {

        return productRepository.addProductToFavoriteList(productId)
    }
}