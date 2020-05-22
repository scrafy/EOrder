package com.eorder.infrastructure.repositories

import com.eorder.domain.factories.Gson
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IProductRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.SearchProduct
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken

class ProductRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager
) : BaseRepository(),
    IProductRepository {

    override fun getProductsByCatalog(
        centerId: Int,
        catalogId: Int
    ): ServerResponse<List<Product>> {

       var products =  listOf<Product>()

        var resp =  ServerResponse<List<Product>>(200, null, ServerData(listOf<Product>(),null))
        return resp
    }

    override fun getProductsBySeller(centerId: Int, sellerId: Int): ServerResponse<List<Product>> {

        var resp =  ServerResponse<List<Product>>(200, null, ServerData(listOf<Product>(),null))
        return resp
    }

    override fun searchProducts(search: SearchProduct, page: Int): ServerResponse<List<Product>> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}ProductDetails/${page}/search",
            search,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Product>>>(
            resp, object : TypeToken<ServerResponse<List<Product>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response

    }

}