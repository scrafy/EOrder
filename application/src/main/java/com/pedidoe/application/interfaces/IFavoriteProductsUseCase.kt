package com.pedidoe.application.interfaces

import android.content.Context
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerResponse

interface IFavoriteProductsUseCase {

    fun getFavoriteProducts( context: Context, searchProduct: SearchProduct ): ServerResponse<List<Product>>
}