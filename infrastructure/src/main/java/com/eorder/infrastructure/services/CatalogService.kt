package com.eorder.infrastructure.services

import com.eorder.infrastructure.interfaces.ICatalogService
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.models.*

class CatalogService(httpClient: IHttpClient) : BaseService(), ICatalogService {

     override fun getCatalogs(): ServerResponse<List<Catalog>> {

        //TODO make a backend call



        var catalogs = mutableListOf<Catalog>()

         catalogs.add(Catalog(1,"Catalog 1",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
         catalogs.add(Catalog(2,"Catalog 2",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
         catalogs.add(Catalog(3,"Catalog 3",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
         catalogs.add(Catalog(4,"Catalog 4",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
         catalogs.add(Catalog(5,"Catalog 5",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))



        var response: ServerResponse<List<Catalog>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    catalogs as List<Catalog>,
                    null
                )
            )
         checkServerErrorInResponse(response)

        return response
    }


    override fun getCatalogsByCenter(centerId:Int) : ServerResponse<List<Catalog>>{
        //TODO make a backend call

        var catalogs = mutableListOf<Catalog>()

        catalogs.add(Catalog(1,"Catalog 1",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
        catalogs.add(Catalog(2,"Catalog 2",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
        catalogs.add(Catalog(3,"Catalog 3",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
        catalogs.add(Catalog(4,"Catalog 4",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))
        catalogs.add(Catalog(5,"Catalog 5",true, Seller(1,5666333333,"Coca Cola","C-569855214","C://Pizarro Nº 18","Barcelona", 38109,Country(1, "España"),"SAP","asdadasd@terra.es",true,Province(1,"Santa Cruz de Tenerife"),Sector(1,"Horeca"))))



        var response: ServerResponse<List<Catalog>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    catalogs as List<Catalog>,
                    null
                )
            )
         checkServerErrorInResponse(response)

        return response
    }

}