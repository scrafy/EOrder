package com.eorder.app.viewmodels.fragments

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.domain.models.Product
import com.eorder.domain.models.SearchProduct
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ProductsViewModel : BaseViewModel() {

    val searchProductsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()


    fun searchProducts(search: SearchProduct, page:Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSearchProductsUseCase().searchProducts(search, page)
            searchProductsResult.postValue(result)
        }
    }



    fun addProductToShop(product: Product) =
        unitOfWorkService.getShopService().addProductToShop(product)

    fun removeProductFromShop(product: Product) =
        unitOfWorkService.getShopService().removeProductFromShop(product)


    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products

    fun writeProductsFavorites(context: Context, productId: Int) {

        unitOfWorkService.getFavoritesService().writeProductToFavorites(context, productId)
    }

    fun loadFavoritesProducts(context: Context?): List<Int>? {

        return unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<List<Int>>(
            context,
            SharedPreferenceKeyEnum.FAVORITE_PRODUCTS.key,
            List::class.java
        )?.map { p -> p.toInt() }

    }

}