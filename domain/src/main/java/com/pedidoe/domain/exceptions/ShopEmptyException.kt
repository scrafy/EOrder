package com.pedidoe.domain.exceptions

import com.pedidoe.domain.enumerations.ErrorCode


class ShopEmptyException(errorCode: ErrorCode, message: String) : BaseException(errorCode, message)
