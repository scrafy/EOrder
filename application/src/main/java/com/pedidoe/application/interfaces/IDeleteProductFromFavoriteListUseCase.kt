package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.ServerResponse

interface IDeleteProductFromFavoriteListUseCase {

    fun DeleteProductFromFavorite(productId: Int): ServerResponse<String>
}