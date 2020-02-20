package services

import interfaces.IServerResponseParseService
import models.infrastructure.Establishment
import models.infrastructure.ServerData
import models.infrastructure.ServerResponse
import extensions.ToDomain


class EstablishmentParseService< T,  V > : IServerResponseParseService< T, V > {

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

