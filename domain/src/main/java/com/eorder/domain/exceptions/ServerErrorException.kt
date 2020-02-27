package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.BaseException


class ServerErrorException : BaseException {

    var serverCodeError: Int? = null


    constructor( serverCodeError:Int?, errorCode: ErrorCode, message: String) : super( errorCode, message ){

        this.serverCodeError = serverCodeError

    }
}