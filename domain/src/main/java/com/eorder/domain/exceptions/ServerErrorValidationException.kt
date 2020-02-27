package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.BaseException
import com.eorder.domain.models.ValidationError


class ServerErrorValidationException : BaseException {

    var serverCodeError: Int? = null
    var validationErrors: List<ValidationError>? = null

    constructor( serverCodeError:Int?, errorCode: ErrorCode, message: String, validationErrors: List<ValidationError> ? ) : super( errorCode, message ){

        this.serverCodeError = serverCodeError
        this.validationErrors = validationErrors
    }
}