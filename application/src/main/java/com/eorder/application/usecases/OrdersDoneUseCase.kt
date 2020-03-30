package com.eorder.application.usecases

import android.content.Context
import com.eorder.application.interfaces.IOrderDoneUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.models.OrdersWrapper
import com.eorder.domain.interfaces.IOrderRepository
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse


class OrdersDoneUseCase(
    private val sharedPreferences: ISharedPreferencesService//esto quitarlo cuando esté implementado el metodo en el backend
    , private val orderRepository: IOrderRepository
) : IOrderDoneUseCase {


    override fun getOrdersDoneByUser(): ServerResponse<List<Order>> {

        return orderRepository.getOrdersDone()
    }

    //este metodo hay que eliminarlo cuando en el backend esté preparado el método de
    //devolver los pedidos realizados
    override fun getOrdersDoneByUser(context: Context): ServerResponse<List<Order>> {

        val orders = sharedPreferences.loadFromSharedPreferences<OrdersWrapper>(
            context,
            "orders_done",
            OrdersWrapper::class.java
        )?.orders ?: mutableListOf<Order>()


        return ServerResponse<List<Order>>(200, null, ServerData(orders, null))
    }

}