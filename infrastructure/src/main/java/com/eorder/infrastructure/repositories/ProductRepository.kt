package com.eorder.infrastructure.repositories

import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.domain.interfaces.IProductRepository

class ProductRepository(private val httpClient: IHttpClient) : BaseRepository(),
    IProductRepository {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        //TODO make a backend call

        var products = mutableListOf<Product>()

        products.add(
            Product(
                1,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                10.56F,
                "Paquete cigarros 20 unidades",
                "Cajetilla Marlboro 20 pitillos",
                "Tabacos",
                "https://i0.pngocean.com/files/275/300/985/marlboro-cigarette-pack-arabs-tobacco-marlboro.jpg"
            )
        )
        products.add(
            Product(
                2,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                30.56F,
                "Juego de sartenes",
                "Juego de dos sartenes",
                "Menaje y cocina",
                "https://teletiendatelevision.com/6686-large_default/pack-2-sartenes-cobre-titanium-copper-28cm.jpg"
            )
        )
        products.add(
            Product(
                3,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                15.56F,
                "Lubricante durex 20 ml",
                "Lubricante durex 20ml",
                "Ocio",
                "https://www.farmaciatedin.es/431-thickbox_default/durex-play-gel-lubricante-original-50ml.jpg"
            )
        )
        products.add(
            Product(
                4,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                8.56F,
                "Paquete pan de molde",
                "Paquete pan de molde",
                "Alimentacion",
                "https://s3.eu-west-2.amazonaws.com/mentta/producto/pan-de-molde-de-trigo-integral-bio-400g.jpg"
            )
        )
        products.add(
            Product(
                5,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                7.56F,
                "Plátano canario",
                "Plátano canario",
                "Frutas y Verduras",
                "https://static3.diariosur.es/www/multimedia/201807/14/media/cortadas/126905082--624x415.jpg"
            )
        )
        products.add(
            Product(
                6,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                5.32F,
                "Saco Patatas 20kg",
                "Saco Patatas 20kg",
                "Hortalizas",
                "https://bonduelle.es/media/zoo/images/patata_781da11b65e9c08ff1fa4c25079f5fdb.jpg"
            )
        )
        products.add(
            Product(
                7,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                100.56F,
                "Sierra mécanica para madera",
                "Sierra mécanica para madera",
                "Herramientas",
                "https://www.colecplan.com/assets/uploads/files/5fddb-motosierra-sierra-mecanica-gasolina-rk-5200-colecplan-p-1.jpg"
            )
        )
        products.add(
            Product(
                8,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                2.56F,
                "Tupper ensalada preparada",
                "Tupper ensalada preparada",
                "Alimentacion",
                "https://www.alcampo.es/media/he3/h45/9322202234910.jpg"
            )
        )
        products.add(
            Product(
                9,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                0F,
                "Caja 12 preservativos durex",
                "Caja 12 preservativos durex",
                "Salud e Higiene",
                "https://www.dosfarma.com/39717-large_default/preservativos-durex-natural-plus-12usensitivo-contacto-total-3u-gratis.jpg"
            )
        )
        products.add(
            Product(
                11,
                0F,
                0F,
                0F,
                21F,
                "IVA",
                0,
                200.89F,
                "Jamon serrano El Pozo 5kg",
                "Jamon serrano El Pozo",
                "Alimentacion",
                "https://yourspanishcorner.com/2274-thickbox_default/jamon-serrano-curado.jpg"
            )
        )

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