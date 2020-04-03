package com.eorder.application.services

import android.content.Context
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.application.interfaces.IFavoritesService
import com.eorder.application.interfaces.ISharedPreferencesService

class FavoritesService(

    private val sharedPreferencesService: ISharedPreferencesService

) : IFavoritesService {

    override fun addProductToFavorites(context: Context, productId: Int) {

        var products = loadFavoriteProducts(context)?.toMutableList() ?: mutableListOf()

        if (products.firstOrNull { p -> p == productId } == null) {

            products.add(productId)

        } else {
            products.remove(productId)
        }
        sharedPreferencesService.writeToSharedPreferences(
            context,
            products,
            SharedPreferenceKeyEnum.FAVORITE_PRODUCTS.key,
            products.javaClass
        )
    }

    override fun loadFavoriteProducts(context: Context): List<Int>? {

        return sharedPreferencesService.loadFromSharedPreferences<List<Int>>(
            context,
            SharedPreferenceKeyEnum.FAVORITE_PRODUCTS.key,
            List::class.java
        )?.map { p -> p.toInt() }
    }
}