package com.pedidoe.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ProductViewModel : BaseMainMenuActionsViewModel() {

    val catalogsResult: MutableLiveData<ServerResponse<List<Catalog>>> = MutableLiveData()
    val centersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()
    val categoriesResult: MutableLiveData<ServerResponse<List<Category>>> = MutableLiveData()
    val searchProductsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()
    val getFavoriteProductsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()
    val getFavoriteProductsIdsResult: MutableLiveData<ServerResponse<List<Int>>> = MutableLiveData()
    val addProductToFavoriteListResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()
    val deleteProductFromFavoriteListResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


    fun getAddProductToCartObservable(): LiveData<Any> =
        unitOfWorkService.getAddProductToShopService().getproductAddedObservable()

    fun getCatalogByCenter(centerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getCatalogsByCenterUseCase().getCatalogsByCenter(centerId)
            catalogsResult.postValue(result)
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

    fun getCategories(catalogId: Int, centreId: Int){

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getCategoriesUseCase().getCategories(catalogId, centreId)
            categoriesResult.postValue(result)
        }
    }

    fun isShopEmpty() = getProductsFromShop().size == 0

    fun isPossibleAddProduct(product: Product, beforeCenter:Center, currentCenter:Center) : Boolean {

        val products = unitOfWorkService.getShopService().getOrder().products

        if ( products.any { x -> x.sellerId != product.sellerId } )
            return false

        if ( beforeCenter.id != currentCenter.id )
            return false

        return true
    }

    fun addCenterToOrder(centerId: Int, centerName: String, centerImageUrl: String?, buyerId:Int?) {

        var order = unitOfWorkService.getShopService().getOrder()

        if (order.center.centerId == null || order.center.centerId == centerId)
        {
            unitOfWorkService.getShopService().getOrder().center.centerId = centerId
            unitOfWorkService.getShopService().getOrder().center.centerName = centerName
            unitOfWorkService.getShopService().getOrder().center.imageUrl = centerImageUrl
            unitOfWorkService.getShopService().getOrder().center.buyerId = buyerId
        }
    }

    fun addSellerToOrder(sellerId: Int, sellerName: String, primaryCode: String) {

        unitOfWorkService.getShopService().getOrder().seller.sellerId = sellerId
        unitOfWorkService.getShopService().getOrder().seller.sellerName = sellerName
        unitOfWorkService.getShopService().getOrder().primaryCode = primaryCode
    }

    fun cleanShop() = unitOfWorkService.getShopService().cleanShop()

    fun resetAmountOfProducts(){

        unitOfWorkService.getShopService().getOrder().products.forEach { p -> p.amount = 0 }
    }

    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCentersUseCase().getCenters()
            centersResult.postValue(result)
        }
    }

    fun addProductToShop(product: Product) =
        unitOfWorkService.getShopService().addProductToShop(product)

    fun removeProductFromShop(product: Product) =
        unitOfWorkService.getShopService().removeProductFromShop(product)

    fun searchProducts(search: SearchProduct, page:Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSearchProductsUseCase().searchProducts(search, page)
            searchProductsResult.postValue(result)
        }
    }

    fun saveProductAsFavorite(productId: Int) {


        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getAddProductToFavoriteListUseCase().AddProductToFavorite(productId)
            addProductToFavoriteListResult.postValue(result)
        }
    }

    fun deleteProductFromFavorites(productId: Int) {


        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getDeleteProductToFavoriteListUseCase().DeleteProductFromFavorite(productId)
            deleteProductFromFavoriteListResult.postValue(result)
        }
    }

    fun getFavoriteProductsIds() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getProductFavoriteListUseCase().GetFavoriteProducts()
            getFavoriteProductsIdsResult.postValue(result)
        }
    }

}
