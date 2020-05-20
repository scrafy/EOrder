package com.eorder.infrastructure.repositories

import com.eorder.domain.factories.Gson
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.models.Category
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.ICategoryRepository
import com.eorder.infrastructure.interfaces.IHttpClient
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