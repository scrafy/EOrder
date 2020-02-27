package com.eorder.domain.interfaces.services

import com.eorder.domain.enumerations.UserRole


interface IJwtTokenService {

    fun isValidToken(token: String): Boolean

    fun getClaimFromToken(token: String, claim: String): Any

    fun getRole(token:String) : UserRole

}