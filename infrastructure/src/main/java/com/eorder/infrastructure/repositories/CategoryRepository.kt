package com.eorder.infrastructure.repositories

import com.eorder.domain.models.Category
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.ICategoryRepository
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.services.ProductsService

class CategoryRepository(private val httpClient: IHttpClient) : BaseRepository(),
    ICategoryRepository {


    override fun getCategories(catalogId: Int): ServerResponse<List<Category>> {
        //TODO make a backend call
        var cont = 1
        val categories = ProductsService.getProducts().filter { it.catallogId == catalogId }
            .groupBy { it.category }.map {

                Category(cont++, it.key, it.value.size)
            }
        
        var response: ServerResponse<List<Category>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    categories,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }
}