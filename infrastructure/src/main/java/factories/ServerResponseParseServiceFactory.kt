package factories

import interfaces.IServerResponseParseService
import interfaces.IServerResponseParseServiceFactory
import models.infrastructure.Establishment
import services.EstablishmentParseService


class ServerResponseParseServiceFactory : IServerResponseParseServiceFactory {


    override fun < T, V > createService(service: String) : IServerResponseParseService< T, V > {

        var output: IServerResponseParseService< T, V > =  when(service){

            "Establishment" -> EstablishmentParseService<Establishment, models.entities.Establishment>() as IServerResponseParseService< T, V >
            else -> throw Exception("")
        }

        return output
    }
}