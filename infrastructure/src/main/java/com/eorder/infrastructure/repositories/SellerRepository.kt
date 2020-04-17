package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient

class SellerRepository(private val httpClient: IHttpClient) : BaseRepository(), ISellerRepository {


    override fun getSeller(sellerId: Int): ServerResponse<Seller> {

        val seller = _getSellers().first { f -> f.id == sellerId }

        var response: ServerResponse<Seller> =
            ServerResponse(
                200,
                null,
                ServerData(
                    seller,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun getSellersByCenter(centerId: Int): ServerResponse<List<Seller>> {

        //TODO make a backend call

        var response: ServerResponse<List<Seller>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    _getSellers(),
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun getSellers(): ServerResponse<List<Seller>> {

        //TODO make a backend call

        var response: ServerResponse<List<Seller>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    _getSellers(),
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    private fun _getSellers(): List<Seller> {

        var sellers = mutableListOf<Seller>()

        sellers.add(
            Seller(
                1,
                65552422454545,
                "MercaBarna",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "mercabarna@gmail.com",
                "Barcelona",
                "Horeca",
                "https://atencioncliente.com/wp-content/uploads/2019/08/mercabarna.jpg"

            )
        )
        sellers.add(
            Seller(
                2,
                65552422454545,
                "Coca Cola",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "mercabarna@gmail.com",
                "Barcelona",
                "Horeca",
                "https://www.cocacolaespana.es/content/dam/one/es/es/lead/logo-coca-cola-1.jpg"
            )
        )
        sellers.add(
            Seller(
                3,
                65552422454545,
                "Cofarma",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "mercabarna@gmail.com",
                "Barcelona",
                "Farmaceutico",
                "https://pbs.twimg.com/profile_images/1149523084/Logo-cofarma-FACE-BOOK2_400x400.jpg"
            )
        )
        sellers.add(
            Seller(
                4,
                65552422454545,
                "Durex",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "mercabarna@gmail.com",
                "Barcelona",
                "Sanitario",
                "https://cdns3-2.primor.eu/img/m/316.jpg"
            )
        )
        return sellers
    }
}