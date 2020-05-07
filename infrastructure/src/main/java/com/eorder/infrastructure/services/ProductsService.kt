package com.eorder.infrastructure.services

import com.eorder.domain.models.Product
import com.eorder.infrastructure.di.UnitOfWorkRepository


class ProductsService {


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
                    13.56F,
                    "Paquete 24 latas cerveza 33ml",
                    "Paquete 24 latas cerveza 33ml",
                    "Bebidas",
                    "https://images-na.ssl-images-amazon.com/images/I/41Nu%2B0wVvEL._AC_SX466_.jpg"

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
                    8.56F,
                    "Paquete de 24 latas soda",
                    "Paquete de 24 latas",
                    "Bebidas",
                    "https://image.freepik.com/vector-gratis/anuncio-realista-limonada_52683-8161.jpg"
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
                    5.45F,
                    "Caja 24 botellas Fontbella 50cl",
                    "Paquete 12 botellas de agua 50cl",
                    "Bebidas",
                    "https://www.readyrefresh.com/medias/sys_master/images/images/hcb/h7e/h00/8797641539614/1941-main-ice-mountain-0.5-l-12-pack-package-top-view.png"
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
                    10.56F,
                    "Té helado Green Tea",
                    "Té helado Green Tea",
                    "Bebidas",
                    "https://image.freepik.com/vector-gratis/ilustracion-realista-vector-te-verde-hielo-publicidad_103044-461.jpg"

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
                    5.32F,
                    "Saco Patatas 20kg",
                    "Saco Patatas 20kg",
                    "Hortalizas",
                    "https://www.comprarpormalaga.com/Files/Images/References/2015/10/3a938dfb-a4b2-4743-b760-a792cb9f2a10/ebf90415-7bc2-419c-97e0-09e6ca69f6b6.jpeg"
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
                    200.89F,
                    "Jamon serrano El Pozo 5kg",
                    "Jamon serrano El Pozo Kg",
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

                    for (d in 1..13) {
                        getAuxProducts().forEach { p ->

                            p.id = productId
                            productId++
                            p.sellerId = i
                            p.catallogId = catalogId
                            p.sellerName = UnitOfWorkRepository.self?.getSellerRepository()
                                ?.getSeller(p.sellerId)?.serverData?.data?.companyName
                            products.add(p)

                        }
                    }
                    catalogId++
                }

            }
            return products.toList()

        }

    }
}