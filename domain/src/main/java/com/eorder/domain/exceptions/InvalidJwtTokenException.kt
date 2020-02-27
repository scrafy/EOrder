package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode

class InvalidJwtTokenException(errorCode: ErrorCode, message: String) : BaseException(errorCode, message){


}