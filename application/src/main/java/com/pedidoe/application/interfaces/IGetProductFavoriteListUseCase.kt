package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.ServerResponse

interface IGetProductFavoriteListUseCase {

    fun GetFavoriteProducts(): ServerResponse<List<Int>>
}