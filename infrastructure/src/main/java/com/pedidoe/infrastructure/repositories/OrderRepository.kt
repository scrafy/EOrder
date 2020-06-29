package com.pedidoe.infrastructure.repositories

import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.interfaces.IOrderRepository
import com.pedidoe.domain.models.Order
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken


class OrderRepository(
    private val configurationManager: IConfigurationManager,
    private val httpClient: IHttpClient
) : BaseRepository(), IOrderRepository {

    override fun confirmOrder(order: Order): ServerResponse<Any> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Orders",
            order,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<Any>>(
            resp, object : TypeToken<ServerResponse<Any>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }


    override fun getOrdersDone(): ServerResponse<List<Order>> {
        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.getJsonResponse (
            "${configurationManager.getProperty("endpoint_url")}Orders/OrdersDone",
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Order>>>(
            resp, object : TypeToken<ServerResponse<List<Order>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

    override fun getOrderTotalsSummary(order: Order): ServerResponse<Order> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Orders/OrderSummary",
            order,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<Order>>(
            resp, object : TypeToken<ServerResponse<Order>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

}