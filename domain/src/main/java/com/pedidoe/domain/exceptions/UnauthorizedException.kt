package com.pedidoe.domain.exceptions

import com.pedidoe.domain.enumerations.ErrorCode

class UnauthorizedException(errorCode:ErrorCode, message:String): BaseException(errorCode, message)