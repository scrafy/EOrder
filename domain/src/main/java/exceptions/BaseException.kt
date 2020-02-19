package exceptions

import enumerations.ErrorCode
import java.lang.Exception

abstract class BaseException: Exception {

    var errorCode: ErrorCode = ErrorCode.UNKNOWN_ERROR

    constructor(errorCode: ErrorCode, message: String) : super(message){

        this.errorCode = errorCode
    }

}