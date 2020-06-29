package com.pedidoe.infrastructure.repositories

import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.models.Category
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.infrastructure.interfaces.ICategoryRepository
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken

class CategoryRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager
) : BaseRepository(),
    ICategoryRepository {


    override fun getCategories(catalogId: Int): ServerResponse<List<Category>> {

        httpClient.addAuthorizationHeader(true)
        val url =
            "${configurationManager.getProperty("endpoint_url")}Category/${catalogId}/Categories"
        val resp = httpClient.getJsonResponse(
            url,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Category>>>(
            resp, object : TypeToken<ServerResponse<List<Category>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }
}