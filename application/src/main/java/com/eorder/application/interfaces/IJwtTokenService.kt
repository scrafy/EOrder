package com.eorder.application.interfaces

import com.eorder.domain.enumerations.UserRole


interface IJwtTokenService {

    fun isValidToken(token: String): Boolean

    fun getClaimFromToken(token: String, claim: String): Any

    fun getRole(token:String) : UserRole

}