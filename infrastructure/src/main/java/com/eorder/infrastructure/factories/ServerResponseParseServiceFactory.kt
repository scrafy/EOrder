package com.eorder.infrastructure.factories

import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.IServerResponseParseService
import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.IServerResponseParseServiceFactory
import com.eorder.infrastructure.models.Establishment
import com.eorder.infrastructure.services.EstablishmentParseService


class ServerResponseParseServiceFactory :
    IServerResponseParseServiceFactory {


    override fun < T, V > createService(service: String) : IServerResponseParseService<T, V> {

        var output: IServerResponseParseService<T, V> =  when(service){

            "Establishment" -> EstablishmentParseService<Establishment, com.eorder.domain.models.Establishment>() as IServerResponseParseService<T, V>
            else -> throw Exception("")
        }

        return output
    }
}