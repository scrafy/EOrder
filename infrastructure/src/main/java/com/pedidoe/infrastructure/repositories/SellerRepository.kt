package com.pedidoe.infrastructure.repositories

import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.interfaces.ISellerRepository
import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken

class SellerRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager
) : BaseRepository(), ISellerRepository {


    override fun getSellers(): ServerResponse<List<Seller>> {

        httpClient.addAuthorizationHeader(true)
        val url = "${configurationManager.getProperty("endpoint_url")}Suppliers"
        val resp = httpClient.getJsonResponse(
            url,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Seller>>>(
            resp, object : TypeToken<ServerResponse<List<Seller>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

}