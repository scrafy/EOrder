package com.eorder.infrastructure.services

import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.interfaces.IProductService
import com.eorder.infrastructure.models.*

class ProductService(httpClient: IHttpClient) : BaseService(), IProductService {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        //TODO make a backend call

        var products = mutableListOf<Product>()

        products.add(Product(1,56965453658,null,21F, Tax(1,"IVA"),8F,true,"Paquete patatas","Paquete patatas",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
        (1,"Catalog 1", true, null),Seller(1,55555,"Las patatas del abuelo","C-563632","Pizarro 18","S/C de Tenerife",36521,Country(1,"España"),"SAP","assad@terra.es",true, Province(1,"S/C de Tenerife"),Sector(1,"Horeca")),
            Format
        (1,"MU")
        ))

        products.add(Product(2,56965453658,null,21F, Tax(1,"IVA"),25.44F,true,"Cigarros","Cigarros",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
                (1,"Catalog 1", true, null),Seller(1,55555,"Marlboro","C-563632","Pizarro 18","S/C de Tenerife",36521,Country(1,"España"),"SAP","assad@terra.es",true, Province(1,"S/C de Tenerife"),Sector(1,"Horeca")),Format
                (1,"MU")))

        products.add(Product(3,56965453658,null,21F, Tax(1,"IVA"),89.5F,true,"Caja 12 unidades de preservativos","Caja 12 unidades de preservativos",20
            ,
            Category(1,"Salud e Higiene",true,null),
            Catalog
                (1,"Catalog 1", true, null),Seller(1,55555,"Durex","C-563632","Pizarro 18","S/C de Tenerife",36521,Country(1,"España"),"SAP","assad@terra.es",true, Province(1,"S/C de Tenerife"),Sector(1,"Horeca")),Format
                (1,"MU")))

        products.add(Product(4,56965453658,null,21F, Tax(1,"IVA"),10F,true,"Pan de molde","Pan de molde",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
                (1,"Catalog 1", true, null),Seller(1,55555,"Pan Bimbo","C-563632","Pizarro 18","S/C de Tenerife",36521,Country(1,"España"),"SAP","assad@terra.es",true, Province(1,"S/C de Tenerife"),Sector(1,"Horeca")),Format
                (1,"MU")))

        products.add(Product(5,56965453658,null,21F, Tax(1,"IVA"),56.8F,true,"Caja 24 huevos frescos","Caja 24 huevos frescos",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
                (1,"Catalog 1", true, null),Seller(1,55555,"Huevos S.L","C-563632","Pizarro 18","S/C de Tenerife",36521,Country(1,"España"),"SAP","assad@terra.es",true, Province(1,"S/C de Tenerife"),Sector(1,"Horeca")),Format
                (1,"MU")))

        products.add(Product(6,56965453658,null,21F, Tax(1,"IVA"),56.8F,true,"Caja 12 huevos frescos","Caja 12 huevos frescos",20
            ,
            Category(1,"Alimentacion",true,null),
            Catalog
                (1,"Catalog 1", true, null),Seller(1,55555,"Huevos S.L","C-563632","Pizarro 18","S/C de Tenerife",36521,Country(1,"España"),"SAP","assad@terra.es",true, Province(1,"S/C de Tenerife"),Sector(1,"Horeca")),Format
                (1,"MU")))

        products.add(Product(7,56965453658,null,21F, Tax(1,"IVA"),87.9F,true,"Juego de 2 sartenes","Juego de 2 sartenes",20
            ,
            Category(1,"Menaje y Cocina",true,null),
            Catalog
                (1,"Catalog 1", true, null),Seller(1,55555,"Tefal","C-563632","Pizarro 18","S/C de Tenerife",36521,Country(1,"España"),"SAP","assad@terra.es",true, Province(1,"S/C de Tenerife"),Sector(1,"Horeca")),Format
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