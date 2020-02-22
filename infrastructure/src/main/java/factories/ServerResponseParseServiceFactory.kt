package services

import interfaces.IServerResponseParseService
import models.infrastructure.Establishment



class ServerResponseParseServiceFactory{


    fun < T, V > createService(service: String) : IServerResponseParseService< T, V > {

        var output: IServerResponseParseService< T, V > =  when(service){

            "Establishment" -> EstablishmentParseService<Establishment,models.entities.Establishment>() as IServerResponseParseService< T, V >
            else -> throw Exception("")
        }

        return output
    }
}