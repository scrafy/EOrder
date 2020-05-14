package com.eorder.domain.models

class ServerError(

    val ErrorMessage: String,
    val ErrorCode: Int? = null,
    val StackTrace: String? = null,
    val ValidationErrors: List<ValidationError>? = null

)

