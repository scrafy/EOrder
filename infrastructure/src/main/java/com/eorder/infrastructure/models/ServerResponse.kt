package com.eorder.infrastructure.models

class ServerResponse<T>(

    var statusCode: Int? = null,
    var serverError: ServerError? = null,
    var serverData: ServerData<T>? = null

)


