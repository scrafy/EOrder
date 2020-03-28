package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IOrderRepository
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient


class OrderRepository(
    private val configurationManager: IConfigurationManager,
    private val httpClient: IHttpClient
) : BaseRepository(), IOrderRepository {

    override fun confirmOrder(order: Order): ServerResponse<Int> {

        /*val stringJson = httpClient.postJsonData(configurationManager.getProperty("endpoint_url"),Gson().toJson(order, Order::class.java),null)
        val result = Gson().fromJson<ServerResponse<Int>>(stringJson, ServerResponse::class.java)
        return result*/

        val resp = ServerResponse(200, null, ServerData(12365, null))
        checkServerErrorInResponse(resp)
        return resp
    }


    override fun getOrdersDone(): ServerResponse<List<Order>> {
        TODO()
    }

    override fun getOrderTotalsSummary(order:Order): ServerResponse<Order>{

        order.products.forEach { p ->

            p.totalBase = p.amount * p.price
            p.totalTaxes = (p.totalBase * p.rate)/100
            p.total = p.totalBase + p.totalTaxes
            order.totalBase += p.totalBase
            order.totalTaxes += p.totalTaxes
            order.total += p.total
        }
        return ServerResponse(200,null,ServerData(order,null))
    }

}