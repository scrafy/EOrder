package com.eorder.infrastructure.com.eorder.infrastructure.interfaces

import com.eorder.infrastructure.models.ServerResponse

interface IServerResponseParseService<T, V> {
    fun parseResponse(input: ServerResponse<T>) : ServerResponse<V>
}