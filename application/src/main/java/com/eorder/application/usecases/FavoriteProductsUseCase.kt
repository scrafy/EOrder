package com.eorder.application.usecases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.IFavoriteProductsUseCase
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.SearchProduct
import com.eorder.domain.models.ServerResponse


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

