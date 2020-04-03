package com.eorder.domain.models

class ServerResponse<T>(

    val statusCode: Int,
    var serverError: ServerError? = null,
    var serverData: ServerData<T>? = null

)


