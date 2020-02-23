package com.eorder.infrastructure.models

class ServerResponse<T>(

    var statusCode: Short? = null,
    var serverError: ServerError? = null,
    var serverData: ServerData<T>? = null

)


