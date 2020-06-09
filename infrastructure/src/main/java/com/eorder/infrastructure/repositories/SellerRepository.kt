package com.eorder.infrastructure.repositories

import com.eorder.domain.factories.Gson
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Center
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken
import java.util.function.Supplier

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