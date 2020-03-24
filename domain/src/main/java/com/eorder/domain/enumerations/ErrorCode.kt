package com.eorder.domain.enumerations

enum class ErrorCode(var code: Int) {

    UNKNOWN_ERROR(100),
    VALIDATION_ERROR(101),
    SERVER_ERROR(102),
    SERVER_VALIDATION_ERROR(103),
    JWT_TOKEN_INVALID(104),
    SHOP_EMPTY(105)
}