package com.eorder.app.com.eorder.app.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.application.interfaces.IOrderDoneUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderDoneViewModel(

    tokenService: IJwtTokenService,
    private val shopService: IShopService,
    sharedPreferencesService: ISharedPreferencesService,
    manageExceptionService: IManageException,
    private val orderDoneUseCase: IOrderDoneUseCase,
    private val loadImagesService: ILoadImagesService

) : BaseMainMenuActionsViewModel(tokenService, sharedPreferencesService, manageExceptionService) {

    private val ordersDoneResult: MutableLiveData<ServerResponse<List<Order>>> = MutableLiveData()


    fun getOrdersDoneResultObservable(): LiveData<ServerResponse<List<Order>>> = ordersDoneResult

    fun getProductsFromShop() = shopService.getOrder().products

    fun getOrdersDoneByUser(context: Context) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            val orders = orderDoneUseCase.getOrdersDoneByUser(context)
            ordersDoneResult.postValue(orders)

        }
    }

    fun loadImages(list: List<UrlLoadedImage>) = loadImagesService.loadImages(list)
    fun isShopEmpty(): Boolean = shopService.isShopEmpty()
    fun setOrder(order: Order) {
        shopService.setOrder(order)
    }
}