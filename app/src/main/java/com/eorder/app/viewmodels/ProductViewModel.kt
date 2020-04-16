package com.eorder.app.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel : BaseMainMenuActionsViewModel() {

    private val catalogsResult: MutableLiveData<ServerResponse<List<Catalog>>> = MutableLiveData()
    private val centersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()
    private val productsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()


    fun getCatalogsResultObservable(): LiveData<ServerResponse<List<Catalog>>> = catalogsResult
    fun getCentersResultObservable(): LiveData<ServerResponse<List<Center>>> = centersResult
    fun getProductsResultObservable(): LiveData<ServerResponse<List<Product>>> = productsResult


    fun getAddFavoriteProductObservable(): LiveData<Any> =
        unitOfWorkService.getAddProductToShopService().getproductAddedObservable()


    fun getCatalogByCenter(centerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getCatalogsByCenterUseCase().getCatalogsByCenter(centerId)
            catalogsResult.postValue(result)
        }
    }

    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCentersUseCase().getCenters()
            centersResult.postValue(result)
        }
    }

    fun getProductsByCatalog(centerId: Int, catalogId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getProductsByCatalogUseCase()
                    .getProductsByCatalog(centerId, catalogId)
            productsResult.postValue(result)
        }
    }

    fun loadImage(img: ImageView, default: Drawable, url: String, isCircle: Boolean) =
        unitOfWorkService.getLoadImageService().loadImage(img, default, url, isCircle)

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
