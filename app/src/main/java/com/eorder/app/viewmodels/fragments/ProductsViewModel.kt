package com.eorder.app.viewmodels.fragments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetProductsByCatalogUseCase
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


class ProductsViewModel(

    private val getProductsByCatalogUseCase: IGetProductsByCatalogUseCase,
    private val loadImageService: ILoadImagesService,
    private val shopService: IShopService,
    private val sharedPreferencesService: ISharedPreferencesService,
    jwtTokenService: IJwtTokenService,
    manageExceptionService: IManageException

) : BaseViewModel(jwtTokenService, manageExceptionService) {

    private val getProductsByCatalogResult: MutableLiveData<ServerResponse<List<Product>>> =
        MutableLiveData()

    fun getProductsByCatalogObservable(): LiveData<ServerResponse<List<Product>>> =
        getProductsByCatalogResult

    fun getProductsByCatalog(catalogId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = getProductsByCatalogUseCase.getProductsByCatalog(catalogId)
            getProductsByCatalogResult.postValue(result)
        }
    }

    fun loadImages(list: List<UrlLoadedImage>) = loadImageService.loadImages(list)
    fun addProductToShop(product: Product) = shopService.addProductToShop(product)
    fun removeProductFromShop(product: Product) = shopService.removeProductFromShop(product)
    fun existProduct(productId: Int) = shopService.existProduct(productId)
    fun cleanProducts() = shopService.cleanProducts()
    fun getProductsFromShop() = shopService.getOrder().products
    fun setProductsToShop(products: MutableList<Product>) {
        shopService.getOrder().products = products
    }

    fun writeProductsFavorites(context: Context?, products: List<Int>) {

        sharedPreferencesService.writeToSharedPreferences(
            context,
            products,
            "favorite_products",
            products.javaClass
        )
    }

    fun loadFavoritesProducts(context: Context?): List<Int>? {

        var list = sharedPreferencesService.loadFromSharedPreferences(
            context,
            "favorite_products",
            List::class.java
        )
        if (list != null) {
            val aux = list as List<Int>
            return aux.map { p -> p.toInt() }
        }
        return null
    }

}