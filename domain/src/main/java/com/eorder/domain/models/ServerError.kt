package com.eorder.domain.models

class ServerError(

    val errorMessage: String,
    val errorCode: Int?,
    val stackTrace: String?,
    var validationErrors: List<ValidationError>?

)

