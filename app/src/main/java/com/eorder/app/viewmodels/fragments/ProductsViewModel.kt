package com.eorder.app.viewmodels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetProductsByCatalogUseCase
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductsViewModel(

    private val getProductsByCatalogUseCase: IGetProductsByCatalogUseCase,
    private val loadImageService: ILoadImagesService,
    val manageExceptionService: IManageException,
    private val shopService: IShopService

) : BaseViewModel() {

    private val getProductsByCatalogResult: MutableLiveData<ServerResponse<List<Product>>> =
        MutableLiveData()

    fun getProductsByCatalogObservable(): LiveData<ServerResponse<List<Product>>> = getProductsByCatalogResult

    fun getProductsByCatalog(catalogId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = getProductsByCatalogUseCase.getProductsByCatalog(catalogId)
            getProductsByCatalogResult.postValue(result)
        }
    }
    fun loadImages(list:List<UrlLoadedImage>) = loadImageService.loadImages(list)
    fun getLoadImageErrorObservable() = loadImageService.returnsloadImageErrorObservable()
    fun addProductToShop(product: Product) = shopService.addProductToShop(product)
    fun removeProductFromShop(product: Product) =  shopService.removeProductFromShop(product)
    fun existProduct(productId: Int) = shopService.existProduct(productId)
    fun cleanProducts() = shopService.cleanProducts()
    fun getProductsFromShop() = shopService.order.products
    fun setProductsToShop(products: MutableList<Product>) {
        shopService.order.products = products
    }



}