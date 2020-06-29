package com.pedidoe.domain.exceptions

import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.models.ValidationError

class ModelValidationException(errorCode: ErrorCode, message: String, val validationErrors: List<ValidationError>): BaseException(errorCode, message)