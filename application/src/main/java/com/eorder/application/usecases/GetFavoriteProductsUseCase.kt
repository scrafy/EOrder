package com.eorder.application.usecases

import android.content.Context
import com.eorder.application.interfaces.IGetFavoriteProductsUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

class GetFavoriteProductsUseCase(

    private val userRepository: IUserRepository,
    private val sharedPreferencesService: ISharedPreferencesService

) : IGetFavoriteProductsUseCase {


    override fun getFavoriteProducts(context: Context): ServerResponse<List<Product>>? {

        var aux: MutableList<*>? = sharedPreferencesService.loadFromSharedPreferences(
            context,
            "favorite_products",
            MutableList::class.java

        ) ?: return null

        return userRepository.getFavoriteProducts((aux as List<Int>).toList().map { i -> i.toInt() })

    }

}

