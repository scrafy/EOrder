package exceptions

import enumerations.ErrorCode

class ValidationException: BaseException {

    constructor(errorCode: ErrorCode, message: String) : super(errorCode, message)
}