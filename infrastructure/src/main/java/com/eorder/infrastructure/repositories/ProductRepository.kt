package com.eorder.infrastructure.repositories

import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.domain.interfaces.IProductRepository
import com.eorder.infrastructure.services.ProductsService

class ProductRepository(private val httpClient: IHttpClient) : BaseRepository(),
    IProductRepository {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        //TODO make a backend call

        var products  = ProductsService.getProducts().filter { p -> p.catallogId == catalogId }


        var response: ServerResponse<List<Product>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    products,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun getProductsBySeller(sellerId: Int): ServerResponse<List<Product>> {

        //TODO make a backend call

        var products  = ProductsService.getProducts().filter { p -> p.sellerId == sellerId }


        var response: ServerResponse<List<Product>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    products,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

}