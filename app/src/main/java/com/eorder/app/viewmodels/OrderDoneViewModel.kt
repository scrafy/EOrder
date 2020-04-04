package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class OrderDoneViewModel : BaseMainMenuActionsViewModel() {

    private val ordersDoneResult: MutableLiveData<ServerResponse<List<Order>>> = MutableLiveData()


    fun getOrdersDoneResultObservable(): LiveData<ServerResponse<List<Order>>> = ordersDoneResult

    fun getOrdersDoneByUser(context: Context) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            val orders = unitOfWorkUseCase.getOrderDoneUseCase().getOrdersDoneByUser(context)
            ordersDoneResult.postValue(orders)

        }
    }

    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun isShopEmpty(): Boolean = unitOfWorkService.getShopService().isShopEmpty()
    fun setOrder(order: Order) {
        unitOfWorkService.getShopService().setOrder(order)
    }
}