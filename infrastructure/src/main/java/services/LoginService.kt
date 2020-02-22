package services

import interfaces.ILoginService
import interfaces.IServerResponseParseServiceFactory
import models.infrastructure.*
import java.util.*
import kotlinx.coroutines.*
import org.json.JSONObject

class LoginService(var httpClient: HttpClient, var serverResponseParseServiceFactory: IServerResponseParseServiceFactory) : ILoginService {



    override fun loguin(loguinRequest: LoginRequest): ServerResponse<models.entities.Establishment> {

        var resp: JSONObject? = null
        runBlocking{
            val job = GlobalScope.launch { // launch a new coroutine in background and continue
                resp = httpClient.getJsonObject("http://dummy.restapiexample.com/api/v1/employees")
            }
            job.join()
        }



       /* var json = Gson().toJson( ServerResponse<Establishment>(
            200, ServerError("Error en el lado de Ferrán", 250, "bla bla bla"),ServerData<Establishment>(
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
                ), Pagination(20, 1, 20), listOf(ValidationError("name","The field phone can not be empty"), ValidationError("name","The field name can not be empty") )
            )
        ))*/

        var t: ServerResponse<models.entities.Establishment> = serverResponseParseServiceFactory.createService<Establishment, models.entities.Establishment>("Establishment").parseResponse(
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