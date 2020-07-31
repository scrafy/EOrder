package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.ServerResponse

interface IAddProductToFavoriteListUseCase {

    fun AddProductToFavorite(productId: Int): ServerResponse<String>
}