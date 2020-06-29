package com.pedidoe.domain.exceptions

import com.pedidoe.domain.enumerations.ErrorCode

class ResourceNotFoundException(errorCode: ErrorCode, message: String) : BaseException(errorCode, message )
