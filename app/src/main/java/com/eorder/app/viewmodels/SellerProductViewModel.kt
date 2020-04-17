package com.eorder.app.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SellerProductViewModel : BaseMainMenuActionsViewModel() {

    private val sellersResult: MutableLiveData<ServerResponse<List<Seller>>> = MutableLiveData()
    private val centersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()
    private val productsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()


    fun getSellersResultObservable(): LiveData<ServerResponse<List<Seller>>> = sellersResult
    fun getCentersResultObservable(): LiveData<ServerResponse<List<Center>>> = centersResult
    fun getProductsResultObservable(): LiveData<ServerResponse<List<Product>>> = productsResult


    fun getAddFavoriteProductObservable(): LiveData<Any> =
        unitOfWorkService.getAddProductToShopService().getproductAddedObservable()

    fun getSellers() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSellersUseCase().sellers()
            sellersResult.postValue(result)
        }
    }

    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCentersUseCase().getCenters()
            centersResult.postValue(result)
        }
    }

    fun getProductsBySeller(centerId: Int, sellerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getProductsBySellerUseCase().getProductsBySeller(centerId, sellerId)
            productsResult.postValue(result)
        }
    }

    fun writeProductsFavorites(context: Context, productId: Int) {

        unitOfWorkService.getFavoritesService().writeProductToFavorites(context, productId)
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