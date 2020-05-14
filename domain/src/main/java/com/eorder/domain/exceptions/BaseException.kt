package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode
import java.lang.Exception

abstract class BaseException: Exception {

    var errorCode: ErrorCode

    constructor(errorCode: ErrorCode, message: String) : super(message){

        this.errorCode = errorCode
    }

}