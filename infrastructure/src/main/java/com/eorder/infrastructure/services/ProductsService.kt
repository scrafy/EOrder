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
                    13.56F,
                    "Paquete 24 latas cerveza San Miguel 33ml",
                    "Paquete 24 latas cerveza San Miguel 33ml",
                    "Alimentacion",
                    "https://images-na.ssl-images-amazon.com/images/I/81w%2B3eCQW5L._AC_SX679_.jpg"
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
                    8.56F,
                    "Paquete de 24 latas Coca Cola",
                    "Paquete de 24 latas Coca Cola 33cl",
                    "Alimentacion",
                    "https://images-na.ssl-images-amazon.com/images/I/61rDPfZ0ReL._AC_SX679_.jpg"
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
                    5.45F,
                    "Caja 24 botellas Fontbella 50cl",
                    "Caja 24 botellas Fontbella 50cl",
                    "Alimentacion",
                    "https://m.media-amazon.com/images/I/41KMEyI8ebL.jpg"
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
                    10.56F,
                    "Paquete cigarros 20 unidades",
                    "Cajetilla Marlboro 20 pitillos",
                    "Tabacos",
                    "https://i0.pngocean.com/files/275/300/985/marlboro-cigarette-pack-arabs-tobacco-marlboro.jpg"

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
                    30.56F,
                    "Juego de dos sartenes",
                    "Juego de dos sartenes",
                    "Menaje y cocina",
                    "https://teletiendatelevision.com/6686-large_default/pack-2-sartenes-cobre-titanium-copper-28cm.jpg"
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
                    7.56F,
                    "Plátano canario",
                    "Plátano canario",
                    "Frutas y Verduras",
                    "https://static3.diariosur.es/www/multimedia/201807/14/media/cortadas/126905082--624x415.jpg"
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
                    5.32F,
                    "Saco Patatas 20kg",
                    "Saco Patatas 20kg",
                    "Hortalizas",
                    "https://bonduelle.es/media/zoo/images/patata_781da11b65e9c08ff1fa4c25079f5fdb.jpg"
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
                    "https://sgfm.elcorteingles.es/SGFM/dctm/MEDIA03/201802/09/00118170302493____1__600x600.jpg"
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
                    6.96F,
                    "Paquete 3 baguettes para horno",
                    "Paquete 3 baguettes para horno",
                    "Alimentacion",
                    "https://cdn-pi.niceshops.com/upload/image/product/large/default/molde-de-horno-para-baguettes-634819-es.jpg"
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
                    "Jamon serrano El Pozo Kg",
                    "Alimentacion",
                    "https://yourspanishcorner.com/2274-thickbox_default/jamon-serrano-curado.jpg"
                )
            )
            aux.add(
                Product(
                    11,
                    1,
                    0F,
                    0F,
                    0F,
                    21.00F,
                    "IVA",
                    0,
                    18.32F,
                    "Queso manchego 1.5Kg",
                    "Queso manchego 1.5Kg",
                    "Alimentacion",
                    "https://cadenaser00.epimg.net/ser/imagenes/2019/04/02/ser_ciudad_real/1554203477_864568_1554203890_noticia_normal_recorte1.jpg"
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