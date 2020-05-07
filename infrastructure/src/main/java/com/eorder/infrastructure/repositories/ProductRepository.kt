package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.IProductRepository
import com.eorder.domain.models.*
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.services.ProductsService

class ProductRepository(private val httpClient: IHttpClient) : BaseRepository(),
    IProductRepository {

    override fun getProductsByCatalog(
        centerId: Int,
        catalogId: Int
    ): ServerResponse<List<Product>> {

        //TODO make a backend call

        var products = ProductsService.getProducts().filter { p -> p.catallogId == catalogId }


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

    override fun getProductsBySeller(centerId: Int, sellerId: Int): ServerResponse<List<Product>> {

        //TODO make a backend call
        var response: ServerResponse<List<Product>>
        var products = ProductsService.getProducts().filter { p -> p.sellerId == sellerId }

        /*if ( centerId == 1){
             response =
                ServerResponse(
                    200,
                    null,
                    ServerData(
                        products,
                        null
                    )
                )
        }else{
             response =
                ServerResponse(
                    200,
                    null,
                    null
                )
        }*/
        response =
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

    override fun searchProducts(search: SearchProduct): ServerResponse<List<Product>> {

        var response: ServerResponse<List<Product>>
        var products =
            ProductsService.getProducts()

        products = products.filter { p -> p.catallogId == search.catalogId }

        if (!search.category.isNullOrEmpty())
            products = products.filter { p -> p.category == search.category }

        if (!search.nameProduct.isNullOrEmpty())
            products = products.filter { p -> p.name.toLowerCase().contains((search.nameProduct as String).toLowerCase()) }



        response =
            ServerResponse(
                200,
                null,
                ServerData(
                    products,
                    Pagination(250, 1, 20)
                )
            )

        checkServerErrorInResponse(response)

        return response

    }

}