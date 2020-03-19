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
                "https://cdn2.iconfinder.com/data/icons/shopping-e-commerce-3/512/imac-products-512.png"
            )
        )
            catalogs.add(
                Catalog(
                    2,
                    "Catalog2",
                    "https://cdn2.iconfinder.com/data/icons/shopping-e-commerce-3/512/imac-products-512.png"
                )
            )
            catalogs.add(
                Catalog(
                    3,
                    "Catalog3",
                    "https://cdn2.iconfinder.com/data/icons/shopping-e-commerce-3/512/imac-products-512.png"
                )
            )
            catalogs.add(
                Catalog(
                    4,
                    "Catalog4",
                    "https://cdn2.iconfinder.com/data/icons/shopping-e-commerce-3/512/imac-products-512.png"
                )
            )
        }

        if (sellerId == 2)
        {
            catalogs.add(
                Catalog(
                    5,
                    "Catalog5",
                    "https://f0.pngfuel.com/png/485/400/orange-illustration-png-clip-art.png"
                )
            )
            catalogs.add(
                Catalog(
                    6,
                    "Catalog6",
                    "https://f0.pngfuel.com/png/485/400/orange-illustration-png-clip-art.png"
                )
            )
            catalogs.add(
                Catalog(
                    7,
                    "Catalog7",
                    "https://f0.pngfuel.com/png/485/400/orange-illustration-png-clip-art.png"
                )
            )
            catalogs.add(
                Catalog(
                    8,
                    "Catalog8",
                    "https://f0.pngfuel.com/png/485/400/orange-illustration-png-clip-art.png"
                )
            )
        }

        if (sellerId == 3)
        {
            catalogs.add(
                Catalog(
                    9,
                    "Catalog9",
                    "https://www.freeiconspng.com/uploads/product-catalog-icon-15.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    10,
                    "Catalog10",
                    "https://www.freeiconspng.com/uploads/product-catalog-icon-15.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    11,
                    "Catalog11",
                    "https://www.freeiconspng.com/uploads/product-catalog-icon-15.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    12,
                    "Catalog12",
                    "https://www.freeiconspng.com/uploads/product-catalog-icon-15.jpg"
                )
            )
        }

        if (sellerId == 4)
        {
            catalogs.add(
                Catalog(
                    13,
                    "Catalog13",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    13,
                    "Catalog13",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    14,
                    "Catalog14",
                    "https://ssuhua.com/wp-content/uploads/2011/09/Icon1-product-catalogue.jpg"
                )
            )
            catalogs.add(
                Catalog(
                    15,
                    "Catalog15",
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