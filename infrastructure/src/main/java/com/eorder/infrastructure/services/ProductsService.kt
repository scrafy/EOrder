package com.eorder.infrastructure.services

import com.eorder.domain.models.Product
import com.eorder.infrastructure.di.UnitOfWorkRepository


class ProductsService{


    companion object {

        private var productId: Int = 1
        private var catalogId: Int = 1


        private val aux = mutableListOf<Product>()
        private val products = mutableListOf<Product>()

        private fun getAuxProducts(): MutableList<Product> {
            aux.clear()
            aux.add(
                Product(
                    1,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    10.56F,
                    "Paquete cigarros 20 unidades",
                    "Cajetilla Marlboro 20 pitillos",
                    "Tabacos",
                    "https://i0.pngocean.com/files/275/300/985/marlboro-cigarette-pack-arabs-tobacco-marlboro.jpg"

                )
            )
            aux.add(
                Product(
                    2,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    30.56F,
                    "Juego de sartenes",
                    "Juego de dos sartenes",
                    "Menaje y cocina",
                    "https://teletiendatelevision.com/6686-large_default/pack-2-sartenes-cobre-titanium-copper-28cm.jpg"
                )
            )
            aux.add(
                Product(
                    3,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    15.56F,
                    "Lubricante durex 20 ml",
                    "Lubricante durex 20ml",
                    "Ocio",
                    "https://www.farmaciatedin.es/431-thickbox_default/durex-play-gel-lubricante-original-50ml.jpg"
                )
            )
            aux.add(
                Product(
                    4,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    8.56F,
                    "Paquete pan de molde",
                    "Paquete pan de molde",
                    "Alimentacion",
                    "https://s3.eu-west-2.amazonaws.com/mentta/producto/pan-de-molde-de-trigo-integral-bio-400g.jpg"
                )
            )
            aux.add(
                Product(
                    5,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    7.56F,
                    "Plátano canario",
                    "Plátano canario",
                    "Frutas y Verduras",
                    "https://static3.diariosur.es/www/multimedia/201807/14/media/cortadas/126905082--624x415.jpg"
                )
            )
            aux.add(
                Product(
                    6,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    5.32F,
                    "Saco Patatas 20kg",
                    "Saco Patatas 20kg",
                    "Hortalizas",
                    "https://bonduelle.es/media/zoo/images/patata_781da11b65e9c08ff1fa4c25079f5fdb.jpg"
                )
            )
            aux.add(
                Product(
                    7,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    100.56F,
                    "Sierra mécanica para madera",
                    "Sierra mécanica para madera",
                    "Herramientas",
                    "https://www.colecplan.com/assets/uploads/files/5fddb-motosierra-sierra-mecanica-gasolina-rk-5200-colecplan-p-1.jpg"
                )
            )
            aux.add(
                Product(
                    8,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    2.56F,
                    "Tupper ensalada preparada",
                    "Tupper ensalada preparada",
                    "Alimentacion",
                    "https://www.alcampo.es/media/he3/h45/9322202234910.jpg"
                )
            )
            aux.add(
                Product(
                    9,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    12.96F,
                    "Caja 12 preservativos durex",
                    "Caja 12 preservativos durex",
                    "Salud e Higiene",
                    "https://www.dosfarma.com/39717-large_default/preservativos-durex-natural-plus-12usensitivo-contacto-total-3u-gratis.jpg"
                )
            )
            aux.add(
                Product(
                    10,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    200.89F,
                    "Jamon serrano El Pozo 5kg",
                    "Jamon serrano El Pozo",
                    "Alimentacion",
                    "https://yourspanishcorner.com/2274-thickbox_default/jamon-serrano-curado.jpg"
                )
            )

            return aux
        }

        fun getProducts(): List<Product> {

            products.clear()
            productId = 1
            catalogId = 1
            for (i in 1..4) {
                for (c in 1..4) {

                    getAuxProducts().forEach { p ->

                        p.id = productId
                        productId++
                        p.sellerId = i
                        p.catallogId = catalogId
                        p.sellerName = UnitOfWorkRepository.self?.getSellerRepository()?.getSeller(p.sellerId)?.serverData?.data?.companyName
                        products.add(p)

                    }
                    catalogId++
                }

            }
            return products.toList()

        }

    }
}