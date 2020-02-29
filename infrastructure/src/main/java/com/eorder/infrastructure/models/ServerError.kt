package com.eorder.infrastructure.models

import com.eorder.domain.models.ValidationError

class ServerError(

    val errorMessage: String,
    val errorCode: Int?,
    val stackTrace: String?,
    var validationErrors: List<ValidationError>?

)

