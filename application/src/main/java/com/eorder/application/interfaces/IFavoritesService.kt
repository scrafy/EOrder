package com.eorder.application.interfaces

import android.content.Context

interface IFavoritesService {

    fun writeProductToFavorites(context: Context, productId: Int)
    fun loadFavoriteProducts(context: Context): List<Int>?
}
