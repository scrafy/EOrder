package com.eorder.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.IOrdersDoneUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.domain.interfaces.IOrderRepository
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse



@RequiresApi(Build.VERSION_CODES.O)
class OrdersDoneUseCase(
     private val orderRepository: IOrderRepository
) : IOrdersDoneUseCase {


    override fun getOrdersDoneByUser(): ServerResponse<List<Order>> {

        return orderRepository.getOrdersDone()
    }

}