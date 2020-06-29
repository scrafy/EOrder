package com.pedidoe.infrastructure.repositories

import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.ICatalogRepository
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.models.Catalog
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken

class CatalogRepository(private val httpClient: IHttpClient, private val configurationManager: IConfigurationManager ) : BaseRepository(),
    ICatalogRepository {


    override fun getCenterCatalogs(centerId: Int): ServerResponse<List<Catalog>> {

        httpClient.addAuthorizationHeader(true)
        val url = "${configurationManager.getProperty("endpoint_url")}Centres/${centerId}/catalogs"
        val resp = httpClient.getJsonResponse (
            url,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Catalog>>>(
            resp, object : TypeToken<ServerResponse<List<Catalog>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }
}