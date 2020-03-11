package com.eorder.infrastructure.services

import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.interfaces.IProductService
import com.eorder.infrastructure.models.*

class ProductService(httpClient: IHttpClient) : BaseService(), IProductService {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        //TODO make a backend call

        var products = mutableListOf<Product>()

        products.add(Product(1,56965453658,null,18.5F, Tax(1,"IVA"),true,"Paquete patatas","Paquete patatas",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
        (1,"Catalog 1", true, null),null,
            Format
        (1,"MU")
        ))

        products.add(Product(2,56965453658,null,18.5F, Tax(1,"IVA"),true,"Cigarros","Cigarros",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
                (1,"Catalog 1", true, null),null,Format
                (1,"MU")))

        products.add(Product(3,56965453658,null,18.5F, Tax(1,"IVA"),true,"Caja 12 unidades de preservativos","Caja 12 unidades de preservativos",20
            ,
            Category(1,"Salud e Higiene",true,null),
            Catalog
                (1,"Catalog 1", true, null),null,Format
                (1,"MU")))

        products.add(Product(4,56965453658,null,18.5F, Tax(1,"IVA"),true,"Pan de molde","Pan de molde",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
                (1,"Catalog 1", true, null),null,Format
                (1,"MU")))

        products.add(Product(5,56965453658,null,18.5F, Tax(1,"IVA"),true,"Caja 24 huevos frescos","Caja 24 huevos frescos",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
                (1,"Catalog 1", true, null),null,Format
                (1,"MU")))

        products.add(Product(5,56965453658,null,18.5F, Tax(1,"IVA"),true,"Juego de 2 sartenes","Juego de 2 sartenes",20
            ,
            Category(1,"Menaje y Cocina",true,null),
            Catalog
                (1,"Catalog 1", true, null),null,Format
                (1,"MU")))


        var response: ServerResponse<List<Product>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    products as List<Product>,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

}