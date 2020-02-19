package models

class ServerResponse<T>(

    var statusCode: Short = 0,
    var serverError: ServerError? = null,
    var serverData: ServerData<T>? = null

)


