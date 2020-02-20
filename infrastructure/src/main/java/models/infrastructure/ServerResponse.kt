package models.infrastructure

class ServerResponse<T>(

    var statusCode: Short? = null,
    var serverError: ServerError? = null,
    var serverData: ServerData<T>? = null

)


