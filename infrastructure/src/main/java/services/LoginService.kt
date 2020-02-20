package services

import interfaces.ILoginService
import models.infrastructure.*
import java.util.*

class LoginService : ILoginService {

    override fun loguin(loguinRequest: LoginRequest): ServerResponse<models.entities.Establishment> {

        var t = ServerResponseParseServiceFactory().createService<Establishment,models.entities.Establishment>("Establishment").parseResponse(
            ServerResponse(
                200, null, ServerData(
                    Establishment(

                        UUID.randomUUID(),
                        "scrafy26@gmail.com",
                        "centerName",
                        "companyName",
                        "taxiD",
                        "Calle Pizarro Nº 18, Edf://Beti bloque B 5ºD",
                        "El Rosario",
                        "Santa Cruz de Tenerife",
                        38109,
                        "España",
                        "sector",
                        true
                    ), Pagination(20, 1, 20), null
                )
            )
        )

        return t

    }

}