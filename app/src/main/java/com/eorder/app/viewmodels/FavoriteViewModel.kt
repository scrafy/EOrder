package com.eorder.app.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel: BaseMainMenuActionsViewModel() {

    private val favoriteProductsResult: MutableLiveData<ServerResponse<List<Product>>> =
        MutableLiveData()


    fun getFavoriteProductsResultObservable(): LiveData<ServerResponse<List<Product>>> =
        favoriteProductsResult

    fun getAddFavoriteProductObservable(): LiveData<Any> = unitOfWorkService.getAddProductToShopService().getproductAddedObservable()

    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products
    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun removeProductFromFavorites(context: Context, productId: Int) {
        unitOfWorkService.getFavoritesService().addProductToFavorites(context, productId)
    }

    fun loadFavoriteProducts(context: Context) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {
            favoriteProductsResult.postValue(
                unitOfWorkUseCase.getFavoriteProductsUseCase().getFavoriteProducts(
                    context
                )
            )
        }

    }

    fun addProductToShop(context: Context, product: Product) {
        unitOfWorkService.getAddProductToShopService().addProductToShop(context, product)
    }
}