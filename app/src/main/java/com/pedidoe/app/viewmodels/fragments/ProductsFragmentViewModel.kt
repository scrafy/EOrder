package com.pedidoe.app.viewmodels.fragments

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.pedidoe.app.viewmodels.BaseViewModel
import com.pedidoe.application.enums.SharedPreferenceKeyEnum
import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.models.Category
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ProductsFragmentViewModel : BaseViewModel() {

    val searchProductsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()
    val getFavoriteProductsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()
    val getFavoriteProductsIdsResult: MutableLiveData<ServerResponse<List<Int>>> = MutableLiveData()
    val addProductToFavoriteListResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()
    val deleteProductFromFavoriteListResult: MutableLiveData<Any> = MutableLiveData()
    val categoriesResult: MutableLiveData<ServerResponse<List<Category>>> = MutableLiveData()


    fun searchProducts(search: SearchProduct, page:Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSearchProductsUseCase().searchProducts(search, page)
            searchProductsResult.postValue(result)
        }
    }

    fun getFavoriteProducts(context: Context, search: SearchProduct) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var ids = unitOfWorkUseCase.getProductFavoriteListUseCase().GetFavoriteProducts()
            var clone = Gson.Create().fromJson<SearchProduct>(Gson.Create().toJson(search), SearchProduct::class.java)
            clone.ProductsIds = ids.ServerData?.Data
            clone.category = null
            var result = unitOfWorkUseCase.getFavoriteProductsUseCase().getFavoriteProducts( context, clone )
            getFavoriteProductsResult.postValue(result)
        }
    }

    fun getCategories(catalogId: Int, centreId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCategoriesUseCase().getCategories(catalogId, centreId)
            categoriesResult.postValue(result)
        }
    }


    fun getOrder() =
        unitOfWorkService.getShopService().getOrder()

    fun addProductToShop(product: Product) =
        unitOfWorkService.getShopService().addProductToShop(product)

    fun removeProductFromShop(product: Product) =
        unitOfWorkService.getShopService().removeProductFromShop(product)


    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products

    fun saveProductAsFavorite(productId: Int) {


        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getAddProductToFavoriteListUseCase().AddProductToFavorite(productId)
            addProductToFavoriteListResult.postValue(result)
        }
    }

    fun deleteProductFromFavorites(productId: Int) {


        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getDeleteProductToFavoriteListUseCase().DeleteProductFromFavorite(productId)
            deleteProductFromFavoriteListResult.postValue(null)
        }
    }

    fun getFavoriteProductsIds() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getProductFavoriteListUseCase().GetFavoriteProducts()
            getFavoriteProductsIdsResult.postValue(result)
        }
    }
}