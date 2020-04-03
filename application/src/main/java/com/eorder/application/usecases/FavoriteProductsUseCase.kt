package com.eorder.application.usecases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.di.UnitOfWorkService
import com.eorder.application.interfaces.IFavoriteProductsUseCase
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository



@RequiresApi(Build.VERSION_CODES.O)
class FavoriteProductsUseCase(

    private val unitOfWorkService: UnitOfWorkService,
    private val unitOfWorkRepository: UnitOfWorkRepository

) : IFavoriteProductsUseCase {


    override fun getFavoriteProducts(context: Context): ServerResponse<List<Product>>? {


        var list =
            unitOfWorkService.getFavoritesService().loadFavoriteProducts(context) ?: return null


        return unitOfWorkRepository.getUserRepository()
            .getFavoriteProducts(list.map { i -> i.toInt() })

    }

}

