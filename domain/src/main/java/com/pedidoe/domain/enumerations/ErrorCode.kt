package com.pedidoe.domain.enumerations

enum class ErrorCode(var code: Int) {

    /*SERVER ERROR CODES*/

    SERVER_INTERNAL_ERROR(500),
    VALIDATION_ERROR(400),
    RESOURCE_NOT_FOUND_ERROR(404),
    UNAUTHORIZED_ERROR(401),


    /*APP ERROR CODES*/
    UNKNOWN_ERROR_CODE(100),
    JWT_TOKEN_INVALID(101),
    SHOP_EMPTY(102);

    companion object {
        private val types = values().associate { it.code to it }

        fun findByValue(value: Int) = types[value]
    }
}