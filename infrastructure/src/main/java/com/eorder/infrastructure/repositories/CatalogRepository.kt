package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.ICatalogRepository
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient

class CatalogRepository(httpClient: IHttpClient) : BaseRepository(), ICatalogRepository {


    override fun getSellerCatalogs(sellerId:Int): ServerResponse<List<Catalog>> {
        //TODO make a backend call



        var catalogs = mutableListOf<Catalog>()

        if (sellerId == 1)
        {
            catalogs.add(
            Catalog(
                1,
                "Catalog1",
                10,
                "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"

            )
        )
            catalogs.add(
                Catalog(
                    2,
                    "Catalog2",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    3,
                    "Catalog3",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    4,
                    "Catalog4",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
        }

        if (sellerId == 2)
        {
            catalogs.add(
                Catalog(
                    5,
                    "Catalog5",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    6,
                    "Catalog6",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    7,
                    "Catalog7",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    8,
                    "Catalog8",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
        }

        if (sellerId == 3)
        {
            catalogs.add(
                Catalog(
                    9,
                    "Catalog9",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    10,
                    "Catalog10",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    11,
                    "Catalog11",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    12,
                    "Catalog12",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
        }

        if (sellerId == 4)
        {
            catalogs.add(
                Catalog(
                    13,
                    "Catalog13",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    14,
                    "Catalog13",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"

                )
            )
            catalogs.add(
                Catalog(
                    15,
                    "Catalog14",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    16,
                    "Catalog15",
                    10,
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
        }




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