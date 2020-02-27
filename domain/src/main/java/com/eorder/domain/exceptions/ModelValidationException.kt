package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.models.ValidationError

class ModelValidationException(errorCode: ErrorCode, message: String, var validationErrors: List<ValidationError>): BaseException(errorCode, message)