package com.eorder.app.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.extensions.round
import com.eorder.application.interfaces.IConfirmOrderUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ShopViewModel(
    private val confirmOrderUseCase: IConfirmOrderUseCase,
    private val shopService: IShopService,
    private val sharedPreferencesService: ISharedPreferencesService,
    jwtTokenService: IJwtTokenService,
    manageExceptionService: IManageException
) : BaseViewModel(jwtTokenService,manageExceptionService) {

    private val confirmOrderResult: MutableLiveData<ServerResponse<Int>> = MutableLiveData()


    fun getConfirmOrderResultObservable(): LiveData<ServerResponse<Int>> = confirmOrderResult

    fun getProducts(): List<Product> = shopService.getOrder().products
    fun getAmountOfProducts(): Int = shopService.getAmountOfProducts()
    fun getTotalTaxBaseAmount(): Float = shopService.getTotalTaxBaseAmount().round(2)
    fun getTotalTaxesAmount(): Float = shopService.getTotalTaxesAmount().round(2)
    fun getTotalAmount(): Float = shopService.getTotalAmount().round(2)
    fun removeProductFromShop(product: Product) = shopService.removeProductFromShop(product)
    fun getSellerName() = shopService.getOrder().seller.companyName
    fun getCenterName() = shopService.getOrder().center.center_name
    fun confirmOrder(){

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {
            val result = confirmOrderUseCase.confirmOrder(shopService.getOrder())
            confirmOrderResult.postValue(result)
        }
    }
    fun writeProductsFavorites(context: Context?, products:List<Int>){

        sharedPreferencesService.writeToSharedPreferences(context, products,"favorite_products", products.javaClass )
    }

}