package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.domain.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ProductViewModel : BaseMainMenuActionsViewModel() {

    val catalogsResult: MutableLiveData<ServerResponse<List<Catalog>>> = MutableLiveData()
    val centersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()
    val productsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()
    val categoriesResult: MutableLiveData<ServerResponse<List<Category>>> = MutableLiveData()
    val searchProductsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()


    fun getAddProductToCartObservable(): LiveData<Any> =
        unitOfWorkService.getAddProductToShopService().getproductAddedObservable()

    fun getCatalogByCenter(centerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getCatalogsByCenterUseCase().getCatalogsByCenter(centerId)
            catalogsResult.postValue(result)
        }
    }

    fun getCategories(catalogId: Int){

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getCategoriesUseCase().getCategories(catalogId)
            categoriesResult.postValue(result)
        }
    }

    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCentersUseCase().getCenters()
            centersResult.postValue(result)
        }
    }


    fun getProductsByCatalog(search: SearchProduct, page:Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getSearchProductsUseCase().searchProducts(search, page)
            productsResult.postValue(result)
        }
    }

    fun addProductToShop(product: Product) =
        unitOfWorkService.getShopService().addProductToShop(product)

    fun removeProductFromShop(product: Product) =
        unitOfWorkService.getShopService().removeProductFromShop(product)


    fun writeProductsFavorites(context: Context, productId: Int) {

        unitOfWorkService.getFavoritesService().writeProductToFavorites(context, productId)
    }

    fun searchProducts(search: SearchProduct, page:Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSearchProductsUseCase().searchProducts(search, page)
            searchProductsResult.postValue(result)
        }
    }

    fun loadFavoritesProducts(context: Context?): List<Int>? {

        return unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<List<Int>>(
            context,
            SharedPreferenceKeyEnum.FAVORITE_PRODUCTS.key,
            List::class.java
        )?.map { p -> p.toInt() }

    }
}
