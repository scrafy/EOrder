package com.eorder.application.usecases

import android.content.Context
import com.eorder.application.interfaces.IGetFavoriteProductsUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

class FavoriteProductsUseCase(

    private val userRepository: IUserRepository,
    private val sharedPreferencesService: ISharedPreferencesService

) : IGetFavoriteProductsUseCase {


    override fun getFavoriteProducts(context: Context): ServerResponse<List<Product>>? {

        var list = sharedPreferencesService.loadFromSharedPreferences<MutableList<Int>>(
            context,
            "favorite_products",
            mutableListOf<Int>()::class.java

        ) ?: return null

        return userRepository.getFavoriteProducts(list.map { i -> i.toInt() })

    }

}

