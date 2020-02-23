package com.eorder.infrastructure.services

import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.IServerResponseParseService
import com.eorder.infrastructure.models.Establishment
import com.eorder.infrastructure.models.ServerData
import com.eorder.infrastructure.models.ServerResponse
import com.eorder.infrastructure.extensions.ToDomain


class EstablishmentParseService< T,  V > :
    IServerResponseParseService<T, V> {

    override fun parseResponse(input: ServerResponse<T>): ServerResponse<V> {

        var output = ServerResponse(
            input.statusCode,
            input.serverError,
            ServerData(
                paginationData = input.serverData?.paginationData,
                validationErrors = input.serverData?.validationErrors,
                data = (input as ServerResponse<Establishment>).serverData?.data?.ToDomain()
            )

        )
        return output as ServerResponse<V>
    }
}

