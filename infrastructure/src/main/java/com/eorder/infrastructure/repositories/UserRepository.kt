package com.eorder.infrastructure.repositories


import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.*
import com.eorder.infrastructure.interfaces.IHttpClient


class UserRepository(private val httpClient: IHttpClient) : BaseRepository(),
    IUserRepository {


    override fun getFavoriteProducts(favorites: List<Int>): ServerResponse<List<Product>> {

        var products = mutableListOf<Product>()

        products.add(
            Product(
                1,
                21F,
                "IVA",
                0,
                89.52369F,
                "Paquete cigarros 20 unidades",
                "Cajetilla Marlboro 20 pitillos",
                "Tabacos",
                "https://i0.pngocean.com/files/275/300/985/marlboro-cigarette-pack-arabs-tobacco-marlboro.jpg"
            )
        )
        products.add(
            Product(
                2,
                21F,
                "IVA",
                0,
                89.52369F,
                "Juego de sartenes",
                "Juego de dos sartenes",
                "Menaje y cocina",
                "https://teletiendatelevision.com/6686-large_default/pack-2-sartenes-cobre-titanium-copper-28cm.jpg"
            )
        )
        products.add(
            Product(
                3,
                21F,
                "IVA",
                0,
                89.52369F,
                "Lubricante durex 20 ml",
                "Lubricante durex 20ml",
                "Ocio",
                "https://www.farmaciatedin.es/431-thickbox_default/durex-play-gel-lubricante-original-50ml.jpg"
            )
        )
        products.add(
            Product(
                4,
                21F,
                "IVA",
                0,
                89.52369F,
                "Paquete pan de molde",
                "Paquete pan de molde",
                "Alimentacion",
                "https://s3.eu-west-2.amazonaws.com/mentta/producto/pan-de-molde-de-trigo-integral-bio-400g.jpg"
            )
        )
        products.add(
            Product(
                5,
                21F,
                "IVA",
                0,
                89.52369F,
                "Plátano canario",
                "Plátano canario",
                "Frutas y Verduras",
                "https://static3.diariosur.es/www/multimedia/201807/14/media/cortadas/126905082--624x415.jpg"
            )
        )
        products.add(
            Product(
                6,
                21F,
                "IVA",
                0,
                89.52369F,
                "Saco Patatas 20kg",
                "Saco Patatas 20kg",
                "Hortalizas",
                "https://bonduelle.es/media/zoo/images/patata_781da11b65e9c08ff1fa4c25079f5fdb.jpg"
            )
        )
        products.add(
            Product(
                7,
                21F,
                "IVA",
                0,
                89.52369F,
                "Sierra mécanica para madera",
                "Sierra mécanica para madera",
                "Herramientas",
                "https://www.colecplan.com/assets/uploads/files/5fddb-motosierra-sierra-mecanica-gasolina-rk-5200-colecplan-p-1.jpg"
            )
        )
        products.add(
            Product(
                8,
                21F,
                "IVA",
                0,
                89.52369F,
                "Tupper ensalada preparada",
                "Tupper ensalada preparada",
                "Alimentacion",
                "https://www.alcampo.es/media/he3/h45/9322202234910.jpg"
            )
        )
        products.add(
            Product(
                9,
                21F,
                "IVA",
                0,
                89.52369F,
                "Caja 12 preservativos durex",
                "Caja 12 preservativos durex",
                "Salud e Higiene",
                "https://www.dosfarma.com/39717-large_default/preservativos-durex-natural-plus-12usensitivo-contacto-total-3u-gratis.jpg"
            )
        )
        products.add(
            Product(
                11,
                21F,
                "IVA",
                0,
                89.52369F,
                "Jamon serrano El Pozo 5kg",
                "Jamon serrano El Pozo",
                "Alimentacion",
                "https://yourspanishcorner.com/2274-thickbox_default/jamon-serrano-curado.jpg"
            )
        )

        var filtered = products.filter { p -> p.id in favorites }
        var response: ServerResponse<List<Product>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    filtered,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun recoverPassword(recoverPasswordRequest: RecoverPassword): ServerResponse<String> {

        //TODO MAKE REAL CALL TO BACKEND
        var response: ServerResponse<String> =
            ServerResponse(
                200,
                null,
                ServerData(
                    "OK",
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun login(loguinRequest: Login): ServerResponse<String> {

        //TODO MAKE REAL CALL TO BACKEND
        var response: ServerResponse<String> =
            ServerResponse(
                200,
                null,
                ServerData(
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1ODUyNDg0ODUsImV4cCI6MTYxNjc4OTE4OSwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjoiTWFuYWdlciIsInVzZXJJZCI6IjEyMyJ9.0BXbwpSK2dnX86tvcV2T0MB2ktNsLKMAsO10LGMlt4Q",
                    null
                )
            )
        checkServerErrorInResponse(response)
        return response
    }

}