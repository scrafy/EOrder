package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.BaseException


class ServerErrorUnhadledException : BaseException {

    var serverCodeError: Int? = null
    var stackTrace: String? = null

    constructor( serverCodeError:Int?, errorCode: ErrorCode, message: String, stackTrace:String?) : super( errorCode, message ){

        this.serverCodeError = serverCodeError
        this.stackTrace = stackTrace
    }
}