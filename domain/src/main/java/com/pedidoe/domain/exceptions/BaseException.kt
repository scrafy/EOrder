package com.pedidoe.domain.exceptions

import com.pedidoe.domain.enumerations.ErrorCode
import java.lang.Exception

abstract class BaseException: Exception {

    var errorCode: ErrorCode

    constructor(errorCode: ErrorCode, message: String) : super(message){

        this.errorCode = errorCode
    }

}