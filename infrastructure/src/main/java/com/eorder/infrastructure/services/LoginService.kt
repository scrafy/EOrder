package com.eorder.infrastructure.services

import com.google.gson.Gson
import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.IServerResponseParseServiceFactory
import com.eorder.infrastructure.models.*
import com.eorder.domain.models.Establishment
import java.util.*

class LoginService(var httpClient: OkHttpClient, var serverResponseParseServiceFactory: IServerResponseParseServiceFactory) :
    ILoginService {



    override fun loguin(loguinRequest: LoginRequest): ServerResponse<Establishment> {

       // var r =  httpClient.getJsonResponse("https://jsonplaceholder.typicode.com/posts/1")

       var json = Gson().toJson(
           ServerResponse<com.eorder.infrastructure.models.Establishment>(
               200,
               ServerError(
                   "Error en el lado de Ferrán",
                   250,
                   "bla bla bla"
               ),
               ServerData<com.eorder.infrastructure.models.Establishment>(
                   com.eorder.infrastructure.models.Establishment(

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
                   ),
                   Pagination(20, 1, 20),
                   listOf(
                       ValidationError(
                           "name",
                           "The field phone can not be empty"
                       ),
                       ValidationError(
                           "name",
                           "The field name can not be empty"
                       )
                   )
               )
           )
       )

        var t: ServerResponse<Establishment> = serverResponseParseServiceFactory.createService<com.eorder.infrastructure.models.Establishment, Establishment>("Establishment").parseResponse(
            ServerResponse(
                200, null, ServerData(
                    com.eorder.infrastructure.models.Establishment(

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