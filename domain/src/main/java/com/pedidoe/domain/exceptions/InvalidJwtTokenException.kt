package com.pedidoe.domain.exceptions

import com.pedidoe.domain.enumerations.ErrorCode

class InvalidJwtTokenException(errorCode: ErrorCode, message: String) : BaseException(errorCode, message)

