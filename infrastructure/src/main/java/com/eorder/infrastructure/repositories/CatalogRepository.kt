package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.ICatalogRepository
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient

class CatalogRepository(private val httpClient: IHttpClient) : BaseRepository(),
    ICatalogRepository {


    override fun getCenterCatalogs(centerId: Int): ServerResponse<List<Catalog>> {
        //TODO make a backend call


        var catalogs = mutableListOf<Catalog>()

        if (centerId == 1) {
            catalogs.add(
                Catalog(
                    1,
                    "Catalog1",
                    10,
                    1,
                    "MercaBarna",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"

                )
            )
            catalogs.add(
                Catalog(
                    2,
                    "Catalog2",
                    10,
                    2,
                    "Coca Cola",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    3,
                    "Catalog3",
                    10,
                    3,
                    "Cofarma",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    4,
                    "Catalog4",
                    10,
                    4,
                    "Durex",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
        }

        if (centerId == 2) {
            catalogs.add(
                Catalog(
                    5,
                    "Catalog5",
                    10,
                    1,
                    "MercaBarna",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"

                )
            )
            catalogs.add(
                Catalog(
                    6,
                    "Catalog6",
                    10,
                    2,
                    "Coca Cola",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    7,
                    "Catalog7",
                    10,
                    3,
                    "Cofarma",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    8,
                    "Catalog8",
                    10,
                    4,
                    "Durex",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
        }

        if (centerId == 3) {
            catalogs.add(
                Catalog(
                    9,
                    "Catalog9",
                    10,
                    1,
                    "MercaBarna",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"

                )
            )
            catalogs.add(
                Catalog(
                    10,
                    "Catalog10",
                    10,
                    2,
                    "Coca Cola",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    11,
                    "Catalog11",
                    10,
                    3,
                    "Cofarma",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    12,
                    "Catalog12",
                    10,
                    4,
                    "Durex",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
        }

        if (centerId == 4) {
            catalogs.add(
                Catalog(
                    13,
                    "Catalog13",
                    10,
                    1,
                    "MercaBarna",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"

                )
            )
            catalogs.add(
                Catalog(
                    14,
                    "Catalog14",
                    10,
                    2,
                    "Coca Cola",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    15,
                    "Catalog15",
                    10,
                    3,
                    "Cofarma",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    16,
                    "Catalog16",
                    10,
                    4,
                    "Durex",
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