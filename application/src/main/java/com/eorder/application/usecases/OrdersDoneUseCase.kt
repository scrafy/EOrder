package com.eorder.application.usecases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.di.UnitOfWorkService
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.application.interfaces.IOrdersDoneUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.models.OrdersWrapper
import com.eorder.domain.interfaces.IOrderRepository
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository
import java.lang.Exception


@RequiresApi(Build.VERSION_CODES.O)
class OrdersDoneUseCase(
    private val unitOfWorkService: UnitOfWorkService,
    private val unitOfWorkRepository: UnitOfWorkRepository
) : IOrdersDoneUseCase {


    override fun getOrdersDoneByUser(): ServerResponse<List<Order>> {

        return unitOfWorkRepository.getOrderRepository().getOrdersDone()
    }

    //este metodo hay que eliminarlo cuando en el backend esté preparado el método de
    //devolver los pedidos realizados

    override fun getOrdersDoneByUser(context: Context): ServerResponse<List<Order>> {


        val orders =
            unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<OrdersWrapper>(
                context,
                SharedPreferenceKeyEnum.ORDERS_DONE.key,
                OrdersWrapper::class.java
            )?.orders ?: mutableListOf<Order>()


        return ServerResponse<List<Order>>(200, null, ServerData(orders, null))
    }

}