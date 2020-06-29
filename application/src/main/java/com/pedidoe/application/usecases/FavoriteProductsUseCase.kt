package com.pedidoe.application.usecases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.IFavoriteProductsUseCase
import com.pedidoe.domain.interfaces.IUserRepository
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerResponse


@RequiresApi(Build.VERSION_CODES.O)
class FavoriteProductsUseCase(

    private val userRepository: IUserRepository

) : IFavoriteProductsUseCase {


    override fun getFavoriteProducts(
        context: Context,
        searchProduct: SearchProduct
    ): ServerResponse<List<Product>> {

        return userRepository.getFavoriteProducts(searchProduct)

    }

}

