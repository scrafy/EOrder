package com.eorder.domain.models

class ServerError(

    val errorMessage: String,
    val errorCode: Int? = null,
    val stackTrace: String? = null,
    val validationErrors: List<ValidationError>? = null

)

