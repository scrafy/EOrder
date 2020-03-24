package com.eorder.domain.interfaces


interface IJwtTokenService {

    fun getToken(): String

    fun cleanToken()

    fun isValidToken(): Boolean

    fun getClaimFromToken(claim: String): Any?

    fun addToken(token: String)

    fun refreshToken(newToken:String)
}