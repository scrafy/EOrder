package com.eorder.domain.interfaces


interface IJwtTokenService {

    var token: String?

    fun isValidToken(): Boolean

    fun getClaimFromToken(claim: String): Any?

    fun addToken(token: String)
}