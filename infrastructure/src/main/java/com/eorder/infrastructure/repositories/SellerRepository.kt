package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient

class SellerRepository(private val httpClient: IHttpClient) : BaseRepository(), ISellerRepository {

    override fun getSellersByCenter(centerId:Int) : ServerResponse<List<Seller>> {

        //TODO make a backend call

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
                "SAP",
                "mercabarna@gmail.com",
                "España",
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
                "SAP",
                "mercabarna@gmail.com",
                "España",
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
                "SAP",
                "mercabarna@gmail.com",
                "España",
                "Barcelona",
                "Farmaceutico",
                "https://scontent.ftfn1-1.fna.fbcdn.net/v/t1.0-9/13332828_1039276322793124_4089080411421114817_n.jpg?_nc_cat=106&_nc_sid=85a577&_nc_ohc=sy4PpKjbQ4sAX8FM-2o&_nc_ht=scontent.ftfn1-1.fna&oh=4584ecca6b1731b42bca209a6f555f0a&oe=5E98C446"
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
                "SAP",
                "mercabarna@gmail.com",
                "España",
                "Barcelona",
                "Sanitario",
                "https://cdns3-2.primor.eu/img/m/316.jpg"
            )
        )

        var response: ServerResponse<List<Seller>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    sellers as List<Seller>,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }
}