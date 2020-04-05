package com.eorder.app.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductViewModel : BaseMainMenuActionsViewModel() {

    private val sellersResult: MutableLiveData<ServerResponse<List<Seller>>> = MutableLiveData()
    private val productsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()
    private val favoriteProductsResult: MutableLiveData<ServerResponse<List<Product>>> =
        MutableLiveData()

    fun getSellersResultObservable(): LiveData<ServerResponse<List<Seller>>> = sellersResult
    fun getProductsResultObservable(): LiveData<ServerResponse<List<Product>>> = productsResult

    fun getSellers() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSellersUseCase().sellers()
            sellersResult.postValue(result)
        }
    }

    fun getProductsBySeller(sellerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getProductsBySellerUseCase().getProductsBySeller(sellerId)
            productsResult.postValue(result)
        }
    }

    fun loadImagesSeller(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun loadImagesProducts(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun getFavoriteProductsResultObservable(): LiveData<ServerResponse<List<Product>>> =
        favoriteProductsResult

    fun getAddFavoriteProductObservable(): LiveData<Any> = unitOfWorkService.getAddProductToShopService().getproductAddedObservable()


    fun writeProductsFavorites(context: Context, productId: Int) {

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

    fun loadFavoritesProducts(context: Context?): List<Int>? {

        return unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<List<Int>>(
            context,
            SharedPreferenceKeyEnum.FAVORITE_PRODUCTS.key,
            List::class.java
        )?.map { p -> p.toInt() }

    }
}