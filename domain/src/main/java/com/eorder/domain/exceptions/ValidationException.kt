package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode

class ValidationException: BaseException {

    constructor(errorCode: ErrorCode, message: String) : super(errorCode, message)
}