package com.eorder.application.interfaces

import android.content.Context
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse

interface IOrdersDoneUseCase {

    fun getOrdersDoneByUser(context: Context): ServerResponse<List<Order>>
    fun getOrdersDoneByUser(): ServerResponse<List<Order>>
}
