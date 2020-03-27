package com.eorder.application.services

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.InvalidJwtTokenException
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IJwtTokenService
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class JwtTokenService(
    private val configurationManager: IConfigurationManager
) : IJwtTokenService {

    private var token: String? = null
    private var jws: Jws<Claims?>? = null

    init {

        this.checkToken()
    }

    override fun cleanToken() {
        this.token = null
    }

    override fun refreshToken(newToken: String) {
        this.token = newToken
    }

    override fun getToken(): String? {

        return this.token
    }

    override fun addToken(token: String) {
        this.jws = this.setToken(token)
        this.token = token
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun isValidToken(): Boolean {

        if (token == null)
            return false

        val date: Date = jws?.body?.expiration!!

        if (date < Date.from(Instant.now())) {
            return false
        }
        return true

    }

    override fun getClaimFromToken(claim: String): Any? {

        return jws?.body?.get(claim)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkToken() {

        val self = this
        GlobalScope.launch(Dispatchers.Default) {

            if (self.token != null && !self.isValidToken())
                self.token = null
            else
                delay(5000)
        }

    }

    private fun setToken(token: String): Jws<Claims?> {

        return try {

            Jwts.parserBuilder()
                .setSigningKey(
                    Keys.hmacShaKeyFor(
                        configurationManager.getProperty("jwt_secret_key").toByteArray(
                            StandardCharsets.UTF_8
                        )
                    )
                )
                .build()
                .parseClaimsJws(token)

        } catch (ex: JwtException) {

            throw InvalidJwtTokenException(
                ErrorCode.JWT_TOKEN_INVALID,
                ex.message ?: "The JWT token is not valid"
            )
        }
    }

}



