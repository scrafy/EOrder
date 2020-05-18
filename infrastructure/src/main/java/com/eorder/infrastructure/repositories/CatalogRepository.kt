package com.eorder.infrastructure.repositories

import com.eorder.domain.factories.Gson
import com.eorder.domain.interfaces.ICatalogRepository
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
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