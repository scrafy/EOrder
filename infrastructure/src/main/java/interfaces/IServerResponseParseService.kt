package interfaces

import models.infrastructure.ServerResponse

interface IServerResponseParseService<T, V> {
    fun parseResponse(input: ServerResponse<T>) : ServerResponse<V>
}