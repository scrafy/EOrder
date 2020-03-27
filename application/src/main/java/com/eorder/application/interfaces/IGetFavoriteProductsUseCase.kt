package com.eorder.application.interfaces

import android.content.Context
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

interface IGetFavoriteProductsUseCase {

    fun getFavoriteProducts(context: Context): ServerResponse<List<Product>>?
}