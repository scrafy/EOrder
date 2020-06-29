package com.pedidoe.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.IOrdersDoneUseCase
import com.pedidoe.domain.interfaces.IOrderRepository
import com.pedidoe.domain.models.Order
import com.pedidoe.domain.models.ServerResponse



@RequiresApi(Build.VERSION_CODES.O)
class OrdersDoneUseCase(
     private val orderRepository: IOrderRepository
) : IOrdersDoneUseCase {


    override fun getOrdersDoneByUser(): ServerResponse<List<Order>> {

        return orderRepository.getOrdersDone()
    }

}