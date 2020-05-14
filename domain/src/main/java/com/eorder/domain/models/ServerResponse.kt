package com.eorder.domain.models

class ServerResponse<T>(

    val StatusCode: Int,
    var ServerError: ServerError? = null,
    var ServerData: ServerData<T>? = null

)


