package com.eorder.domain.exceptions

import com.eorder.domain.enumerations.ErrorCode


class ShopEmptyException(errorCode: ErrorCode, message: String) : BaseException(errorCode, message)
