package com.eorder.infrastructure.services

import com.eorder.infrastructure.interfaces.ICenterRepository
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.models.*

class CenterRepository(httpClient: IHttpClient) : ICenterRepository {

    override fun getCenters(userId: Int): ServerResponse<List<Center>> {

        //TODO make a backend call

        var centers = mutableListOf<Center>()

        centers.add(Center(1,"Centro 1", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))
        centers.add(Center(1,"Centro 2", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))
        centers.add(Center(1,"Centro 3", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))
        centers.add(Center(1,"Centro 4", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))
        centers.add(Center(1,"Centro 5", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))
        centers.add(Center(1,"Centro 6", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))
        centers.add(Center(1,"Centro 7", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))
        centers.add(Center(1,"Centro 8", "C://Pizarro Nº 18", "Santa Cruz de Tenerife", 38109, Country(1,"España"), "asdadasd@terra.es",true, Province(1,"Santa Cruz de Tenerife"), Sector(1,"Farmacéutico"),Rate(1, "Tarifa 1"),
            Buyer(1,"Benito y Compañia S.L","C-89566225","C://Pizarro Nº18 5ºD","Santa Cruz de Tenerife",38106,Country(1,"España"),true,Province(1,"Santa Cruz de Tenerife"))))


        var response: ServerResponse<List<Center>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    centers as List<Center>,
                    null
                )
            )
       // checkServerErrorInResponse(response)

        return response
    }

}