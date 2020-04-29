package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.*
import com.eorder.infrastructure.interfaces.IHttpClient

class CenterRepository(private val httpClient: IHttpClient) : BaseRepository(), ICenterRepository {


    override fun checkCenterActivationCode(code: CenterCode): ServerResponse<Boolean> {

        var response: ServerResponse<Boolean> =
            ServerResponse(
                200,
                null,
                ServerData(false, null)

            )
        checkServerErrorInResponse(response)

        return response
    }


    override fun getUserCenters(): ServerResponse<List<Center>> {

        //TODO make a backend call

        var centers = mutableListOf<Center>()

        centers.add(
            Center(
                1,
                "Centro 1",
                "C://Pizarro Nº 18",
                "Santa Cruz de Tenerife",
                38109,
                "España",
                "center1@gmail.com",
                "S/C de Tenerife",
                "Horeca",
                "https://erfinternational.es/img/local-comercial-nueva-construccion-torrevieja-centro_923_lg.jpg"

            )
        )

        centers.add(
            Center(
                2,
                "Centro 2",
                "C://Pizarro Nº 18",
                "Santa Cruz de Tenerife",
                38109,
                "España",
                "center1@gmail.com",
                "S/C de Tenerife",
                "Farmaceútico",
                "https://diariodealicante.net/wp-content/uploads/2019/11/fachada-de-farmacia-33-1068x801.jpg"

            )
        )

        centers.add(
            Center(
                3,
                "Centro 3",
                "C://Pizarro Nº 18",
                "Santa Cruz de Tenerife",
                38109,
                "España",
                "center1@gmail.com",
                "S/C de Tenerife",
                "Horeca",
                "https://media-cdn.tripadvisor.com/media/photo-s/14/b3/3e/cf/restaurante-ispal.jpg"

            )
        )

        centers.add(
            Center(
                4,
                "Centro 4",
                "C://Pizarro Nº 18",
                "Santa Cruz de Tenerife",
                38109,
                "España",
                "center1@gmail.com",
                "S/C de Tenerife",
                "Horeca",
                "https://restaurantemarkina.com/wp-content/uploads/comedor-superior-01-restaurante-markina.jpg"

            )
        )

        var response: ServerResponse<List<Center>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    centers as List<Center>,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

}