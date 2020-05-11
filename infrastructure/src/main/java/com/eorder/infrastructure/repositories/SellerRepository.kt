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
                "MaxClean",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "maxclean@gmail.com",
                "Barcelona",
                "Horeca",
                "https://1.bp.blogspot.com/-R1wXSr4Amtk/ULQ3AXhkCVI/AAAAAAAABfI/yArzvXfBdfo/s1600/Logo+max+clean-01.jpg"

            )
        )
        sellers.add(
            Seller(
                2,
                65552422454545,
                "Drinks",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "mercabarna@gmail.com",
                "Barcelona",
                "Horeca",
                "https://thumbs.dreamstime.com/b/drinks-vector-logo-badge-green-cocktail-bar-calligraphy-logotype-hand-written-modern-lettering-cafe-menu-vintage-retro-style-167082304.jpg"
            )
        )
        sellers.add(
            Seller(
                3,
                65552422454545,
                "Bread Factory",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "mercabarna@gmail.com",
                "Barcelona",
                "Horeca",
                "https://www.brandcrowd.com/gallery/brands/pictures/picture1558517543547.jpg"
            )
        )
        sellers.add(
            Seller(
                4,
                65552422454545,
                "Fruits & Vegetables",
                "C-523665522",
                "C//Manzanillo Nº3 Polígono 7",
                "Barcelona",
                38005,
                "España",
                "SAP",
                "mercabarna@gmail.com",
                "Barcelona",
                "Horeca",
                "https://image.freepik.com/free-vector/fruit-vegetables-logo_7085-159.jpg"
            )
        )
        return sellers
    }
}