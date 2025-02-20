package com.pedidoe.application.services

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.extensions.convertToLocalDateTime
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.exceptions.InvalidJwtTokenException
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.interfaces.IJwtTokenService
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat


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

        var exp = jws?.body?.expiration!!.convertToLocalDateTime()

        if ( jws?.body?.expiration!!.timezoneOffset > 0 )
           exp = exp.plusMinutes(jws?.body?.expiration!!.timezoneOffset.toLong())
        else
           exp = exp.minusMinutes( (jws?.body?.expiration!!.timezoneOffset * -1).toLong())

        if ( exp < LocalDateTime.now(ZoneOffset.UTC)) {
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
                .setAllowedClockSkewSeconds(120)
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



