package com.pedidoe.infrastructure.repositories

import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.interfaces.IProductRepository
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerData
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken

class ProductRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager
) : BaseRepository(),
    IProductRepository {


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

    override fun addProductToFavoriteList(productId: Int): ServerResponse<String> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.postNoBody(
            "${configurationManager.getProperty("endpoint_url")}ProductDetails/${productId}/addfavoriteproduct",
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<String>>(
            resp, object : TypeToken<ServerResponse<String>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response

    }

    override fun deleteProductFromFavoriteList(productId: Int): ServerResponse<String> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.remove(
            "${configurationManager.getProperty("endpoint_url")}ProductDetails/${productId}/removefavoriteproduct",
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<String>>(
            resp, object : TypeToken<ServerResponse<String>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response

    }

    override fun getProductsFromFavoriteList(): ServerResponse<List<Int>> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.getJsonResponse(
            "${configurationManager.getProperty("endpoint_url")}ProductDetails/favoriteproducts",
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Int>>>(
            resp, object : TypeToken<ServerResponse<List<Int>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response

    }

}